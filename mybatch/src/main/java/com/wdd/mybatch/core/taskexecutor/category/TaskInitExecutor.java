package com.wdd.mybatch.core.taskexecutor.category;

import com.google.common.collect.Lists;
import com.wdd.mybatch.core.common.utils.UuidUtils;
import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.entity.TaskSlice;
import com.wdd.mybatch.core.enums.TaskStatusEnum;
import com.wdd.mybatch.core.taskexecutor.BaseTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TaskInitExecutor extends BaseTaskExecutor {

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Override
    public TaskStatusEnum support() {
        return TaskStatusEnum.INIT;
    }

    @Override
    protected void doExecute(Task task) {
        // 1.任务预处理
        taskRunnerFactory.decorateTask(task);

        broadcastTask(task);

        // 2.任务分片
        List<TaskSlice> taskSliceList = taskRunnerFactory.splitTask(task);
        if (CollectionUtils.isEmpty(taskSliceList)) {
            return;
        }
        log.info("任务{}分片数量：{}", task.getId(), taskSliceList.size());

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                long startTime = System.currentTimeMillis();
                List<List<TaskSlice>> partitionList = Lists.partition(taskSliceList,
                        taskScheduleConfig.getSliceBatchInsertSize(task.getTaskType()));
                for (List<TaskSlice> taskSlices : partitionList) {
                    batchInsert(task, taskSlices);
                }

                // 取最新
                Task currentTask = taskRepository.queryById(task.getId());
                if (!currentTask.isPause()) {
                    currentTask.start();
                    taskRepository.updateById(currentTask);
                }

                broadcastTask(task);
                log.info("任务{}，分片创建耗时：{}秒", task.getId(), (System.currentTimeMillis() - startTime) / 1000);
            }
        });
    }

    /**
     * 批量保存，存在的话跳过
     *
     * @param task
     * @param taskSliceList
     */
    public void batchInsert(Task task, List<TaskSlice> taskSliceList) {
        List<String> businessIds = taskSliceList.stream().
                map(TaskSlice::getBusinessId).collect(Collectors.toList());
        Set<String> existBusinessIdSet = taskSliceRepository.queryByTaskIdBizIds(
                task.getId(), businessIds);
        log.info("分片已存在跳过：{}", existBusinessIdSet);

        if (!CollectionUtils.isEmpty(existBusinessIdSet)) {
            //过滤
            taskSliceList = taskSliceList.stream().filter(taskSlice -> !existBusinessIdSet.contains(taskSlice.getBusinessId()))
                    .collect(Collectors.toList());
        }

        //初始化并统计
        taskSliceList = taskSliceList.stream().map(taskSlice -> {
            log.info("分片不存在新创建：{}", taskSlice);
            taskSlice.init(UuidUtils.getId(), task);
            return taskSlice;
        }).collect(Collectors.toList());

        //批量插入
        taskSliceRepository.insertList(taskSliceList);
    }
}
