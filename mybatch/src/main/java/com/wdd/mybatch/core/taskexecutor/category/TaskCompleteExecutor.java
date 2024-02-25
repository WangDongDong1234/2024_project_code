package com.wdd.mybatch.core.taskexecutor.category;

import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.enums.TaskSliceStatusEnum;
import com.wdd.mybatch.core.enums.TaskStatusEnum;
import com.wdd.mybatch.core.taskexecutor.BaseTaskExecutor;
import com.wdd.mybatch.core.vo.TaskSliceProgress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class TaskCompleteExecutor extends BaseTaskExecutor {
    @Override
    public TaskStatusEnum support() {
        return TaskStatusEnum.BEFORE_COMPLETE;
    }

    @Override
    protected void doExecute(Task task) {
        TaskSliceProgress successFailCnt = taskSliceRepository.sumSuccessFailCntByTaskId(
                task.getId());

        long failSliceCnt = taskSliceRepository.countByTaskIdStatus(task.getId(),
                TaskSliceStatusEnum.FAIL);
        log.info("失败分片数量:{}", failSliceCnt);

        task.finish(failSliceCnt, successFailCnt.getSuccessCnt(), successFailCnt.getFailCnt(),
                successFailCnt.getSkipCnt());

        if (task.getTaskStatus() == TaskStatusEnum.FAIL) {
            taskRunnerFactory.fail(task, "失败分片数量: " + failSliceCnt);
        } else if (task.getTaskStatus() == TaskStatusEnum.SUCCESS) {
            taskRunnerFactory.success(task);
        }

        //可以接管道任务
//        List<PipelineTaskRunner> list = pipelineTaskRunnerMap.get(task.getTaskType());
//        if (CollectionUtils.isNotEmpty(list)) {
//            list.forEach(runner -> runner.trigger(task));
//        }

        taskRepository.updateById(task);
    }
}
