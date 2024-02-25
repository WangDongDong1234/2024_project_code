package com.wdd.mybatch.core.taskexecutor.category;

import com.wdd.mybatch.core.common.utils.ExceptionUtil;
import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.entity.TaskSlice;
import com.wdd.mybatch.core.enums.TaskSliceStatusEnum;
import com.wdd.mybatch.core.enums.TaskStatusEnum;
import com.wdd.mybatch.core.taskexecutor.BaseTaskExecutor;
import com.wdd.mybatch.core.vo.TaskSliceProgress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TaskRunningExecutor extends BaseTaskExecutor {
    @Override
    protected void doExecute(Task task) {
        List<TaskSlice> runningTaskSliceList = taskSliceRepository.queryByTaskIdStatus(task,
                TaskSliceStatusEnum.RUNNING,
                taskScheduleConfig.getMaxSliceExecuteCntPerTask(task.getTaskType()) + 1);

        //分片执行超过指定时间，重置状态
        resetRunningTaskSlice(task, runningTaskSliceList);

        if (runningTaskSliceList.size() >= taskScheduleConfig.getMaxSliceExecuteCntPerTask(
                task.getTaskType())) {
            log.info("任务{}调度中的分片数量已超限：{}", task.getId(), runningTaskSliceList.size());
            return;
        }

        // 先捞待继续执行的
        List<TaskSlice> continueTaskSliceList = taskSliceRepository.queryByTaskIdStatus(task,
                TaskSliceStatusEnum.CONTINUE,
                taskScheduleConfig.getSingleSliceExecuteCnt(task.getTaskType()));
        List<TaskSlice> taskSliceList = new ArrayList<>(continueTaskSliceList);
        log.info("CONTINUE的数量：{}", continueTaskSliceList.size());
        // 不够，再捞初始的
        if (taskSliceList.size() < taskScheduleConfig.getSingleSliceExecuteCnt(
                task.getTaskType())) {
            List<TaskSlice> initTaskSliceList = taskSliceRepository.queryByTaskIdStatus(task,
                    TaskSliceStatusEnum.INIT,
                    taskScheduleConfig.getSingleSliceExecuteCnt(task.getTaskType())
                            - taskSliceList.size());
            log.info("INIT的数量：{}", initTaskSliceList.size());
            taskSliceList.addAll(initTaskSliceList);
        }
        log.info("调度slice总数量：{}", taskSliceList.size());

        // 如果没有需要跑的分片，则所有分片执行完成
        if (taskSliceList.isEmpty()) {
            if (runningTaskSliceList.isEmpty()) {
                task.setTaskStatus(TaskStatusEnum.SLICE_MERGE);
                taskRepository.updateById(task);
            }
            return;
        }

        for (TaskSlice taskSlice : taskSliceList) {
            taskSlice.start();
            taskSliceRepository.updateById(taskSlice);

            try {
                // 发送分片消息
                TaskSliceProgress taskSliceProgress = taskRunnerFactory.runSlice(task, taskSlice);
                if (taskSliceProgress != null) {
                    taskSlice.syncRefreshProgress(taskSliceProgress);
                    taskSliceRepository.updateById(taskSlice);
                }
            } catch (Exception e) {
                log.error("分片执行出错：{}, e={}", taskSlice, ExceptionUtil.getStackTrace(e));
                taskSlice.fail(e.getMessage());
                taskSliceRepository.updateById(taskSlice);
            }
        }
    }

    /**
     * 长时间未结束任务自动重跑
     *
     * @param task
     * @param runningTaskSliceList
     */
    private void resetRunningTaskSlice(Task task, List<TaskSlice> runningTaskSliceList) {
        log.info("RUNNING的分片数量：{}", runningTaskSliceList.size());

        int timeout = taskScheduleConfig.getTaskSliceTimeoutMinutes(task.getTaskType());

        if (!CollectionUtils.isEmpty(runningTaskSliceList) && timeout > 0) {
            LocalDateTime now = LocalDateTime.now();

            int resetTaskSliceCnt = 0;

            for (TaskSlice taskSlice : runningTaskSliceList) {
                long runningMinutes = taskSlice.getUpdateTime().until(now, ChronoUnit.MINUTES);
                if (runningMinutes > timeout) {
                    taskSlice.setSliceStatus(TaskSliceStatusEnum.CONTINUE);
                    taskSliceRepository.updateById(taskSlice);

                    log.info(" 重置【{}】超时分片taskSlice={}为CONTINUE成功",
                            task.getTaskType().getTaskName(), taskSlice.getId());
                    ++resetTaskSliceCnt;
                }
            }

            if (resetTaskSliceCnt > 0) {
                String s = String.format(
                        " 【%s】有%s个分片因超时%s分钟被重置续跑\ntaskId=%s",
                        task.getTaskType().getTaskName(), resetTaskSliceCnt, timeout, task.getId());
                log.info(s);
            }
        }
    }

    @Override
    public TaskStatusEnum support() {
        return TaskStatusEnum.SLICE_RUNNING;
    }

}
