package com.wdd.mybatch.core.taskexecutor;

import com.wdd.mybatch.core.config.task.TaskScheduleConfig;
import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.factory.TaskRunnerFactory;
import com.wdd.mybatch.core.repository.TaskRepository;
import com.wdd.mybatch.core.repository.TaskSliceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class BaseTaskExecutor implements TaskExecutor{

    @Autowired
    protected TaskRunnerFactory taskRunnerFactory;

    @Autowired
    protected TaskScheduleConfig taskScheduleConfig;

    @Autowired
    protected TaskRepository taskRepository;

    @Autowired
    protected TaskSliceRepository taskSliceRepository;
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
     * 任务状态广播
     * TaskInitExecutor,
     * @param task
     */
    protected void broadcastTask(Task task){
        log.info("任务状态广播:{}",task);
    }
}
