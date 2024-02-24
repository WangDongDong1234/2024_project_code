package com.wdd.mybatch.core.factory;

import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.enums.TaskStatusEnum;
import com.wdd.mybatch.core.enums.TaskTypeEnum;
import com.wdd.mybatch.core.taskexecutor.TaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class TaskExecutorFactory implements InitializingBean {

    @Autowired
    private List<TaskExecutor> taskExecutorList;

    private final Map<TaskStatusEnum,TaskExecutor> taskExecutorMap = new ConcurrentHashMap<>();


    @Override
    public void afterPropertiesSet() throws Exception {
        for(TaskExecutor taskExecutor:taskExecutorList){
            taskExecutorMap.put(taskExecutor.support(),taskExecutor);
            log.info("注册任务处理器：{}",taskExecutor.support());
        }

    }

    public void createTask(TaskTypeEnum taskTypeEnum) {
        Task dummyTask = new Task();
        dummyTask.setTaskType(taskTypeEnum);
        taskExecutorMap.get(TaskStatusEnum.CREATE).execute(dummyTask);
    }
}
