package com.wdd.mybatch.core.taskexecutor.category;

import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.enums.TaskStatusEnum;
import com.wdd.mybatch.core.taskexecutor.BaseTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskMergeExecutor extends BaseTaskExecutor {
    @Override
    public TaskStatusEnum support() {
        return TaskStatusEnum.SLICE_MERGE;
    }

    @Override
    protected void doExecute(Task task) {
        taskRunnerFactory.mergeSplice(task);

        // 取最新
        Task currentTask = taskRepository.queryById(task.getId());
        if (currentTask.isPause() || currentTask.isFinal()) {
            return;
        }

        task.setTaskStatus(TaskStatusEnum.BEFORE_COMPLETE);
        taskRepository.updateById(task);
    }
}
