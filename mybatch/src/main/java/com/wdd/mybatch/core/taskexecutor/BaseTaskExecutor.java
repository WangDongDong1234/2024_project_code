package com.wdd.mybatch.core.taskexecutor;

import com.wdd.mybatch.core.entity.Task;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseTaskExecutor implements TaskExecutor{


    @Override
    public void execute(Task task) {
        log.info("{}执行任务{}",getClass().getSimpleName(),task);
        try{
            doExecute(task);
        }catch (Exception e){
            log.error("执行出现异常，task={}",task.getId());
            throw new RuntimeException(e);
        }
        log.info("执行完成，task={}",task.getId());
    }

    /**
     * 任务执行
     * @param task
     */
    protected abstract void doExecute(Task task);

    /**
     * 任务状态广播 TODO
     * @param task
     */
    protected void broadcastTask(Task task){
        log.info("任务状态广播:{}",task);
    }
}
