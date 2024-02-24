package com.wdd.mybatch.core.taskexecutor.category;

import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.enums.TaskStatusEnum;
import com.wdd.mybatch.core.taskexecutor.BaseTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskInitExecutor extends BaseTaskExecutor {
    @Override
    protected void doExecute(Task task) {

    }

    @Override
    public TaskStatusEnum support() {
        return TaskStatusEnum.INIT;
    }
}
