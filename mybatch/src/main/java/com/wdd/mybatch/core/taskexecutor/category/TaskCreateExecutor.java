package com.wdd.mybatch.core.taskexecutor.category;

import com.wdd.mybatch.core.config.task.TaskScheduleConfig;
import com.wdd.mybatch.core.consts.Consts;
import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.enums.TaskStatusEnum;
import com.wdd.mybatch.core.enums.TaskTypeEnum;
import com.wdd.mybatch.core.taskexecutor.BaseTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Slf4j
@Component
public class TaskCreateExecutor extends BaseTaskExecutor {

    @Autowired
    @Qualifier(Consts.ASYNC_TAK_POOL)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private TaskScheduleConfig taskScheduleConfig;

    @Override
    protected void doExecute(Task task) {
        int startDay =taskScheduleConfig.getTaskCreateStartDay(task.getTaskType());
        LocalDateTime today =LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        for(int i =startDay;i<=0;i++){
            LocalDateTime businessTime =today.plusDays(i);
            log.info("尝试异步创建人物:{},{}",task.getTaskType(),businessTime);

            threadPoolTaskExecutor.execute(()->generateTaskOneDay(businessTime,task.getTaskType()));
        }

    }

    private void generateTaskOneDay(LocalDateTime businessTime, TaskTypeEnum taskType) {
        try{

        }catch (Exception e){
            log.error("generateTaskOneDay 异常:{}",e.getMessage());
        }
    }

    @Override
    public TaskStatusEnum support() {
        return TaskStatusEnum.CREATE;
    }
}
