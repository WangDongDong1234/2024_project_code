package com.wdd.mybatch.core.taskexecutor;

import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.enums.TaskStatusEnum;

/**
 * 任务执行器
 */
public interface TaskExecutor {

    /**
     * 任务执行支持的状态
     * @return
     */
    TaskStatusEnum support();

    /**
     * 任务执行
     * @param task
     */
    void execute(Task task);
}
