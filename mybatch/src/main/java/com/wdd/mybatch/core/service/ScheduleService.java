package com.wdd.mybatch.core.service;

import com.wdd.mybatch.core.config.task.TaskScheduleConfig;
import com.wdd.mybatch.core.enums.TaskTypeEnum;
import com.wdd.mybatch.core.factory.TaskExecutorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScheduleService {

    @Autowired
    private TaskScheduleConfig taskScheduleConfig;

    @Autowired
    private TaskExecutorFactory taskExecutorFactory;

    public void createTask() {
        for(TaskTypeEnum taskTypeEnum: TaskTypeEnum.values()){
            try{
                if(allow(taskTypeEnum)){
                    taskExecutorFactory.createTask(taskTypeEnum);
                }
            }catch (Exception e){
                log.error("任务创建错误：{}，{}",taskTypeEnum,e.getMessage());
            }
        }
    }

    /**
     * 是否允许创建任务，true为允许，false为不允许
     * @param taskTypeEnum
     * @return
     */
    private boolean allow(TaskTypeEnum taskTypeEnum) {
        if(taskScheduleConfig.isTaskTypeStopSchedule(taskTypeEnum)){
            log.info("任务暂停调度，跳过创建：{}",taskTypeEnum);
            return false;
        }

        if(taskScheduleConfig.matchTaskCreateIntervalMinutes(taskTypeEnum)){
            log.info("任务不满足自动创建时间间隔，跳过创建：{}",taskTypeEnum);
            return false;
        }

        if(taskScheduleConfig.inTaskCreateTimeRange(taskTypeEnum)){
            log.info("任务不满足自动创建时间区间，跳过创建：{}",taskTypeEnum);
            return false;
        }

        if(taskScheduleConfig.inTaskCreateWeekDayRange(taskTypeEnum)){
            log.info("任务不满足自动创建周/天区间，跳过创建：{}",taskTypeEnum);
            return false;
        }

        if(taskScheduleConfig.inTaskCreateMonthDayRange(taskTypeEnum)){
            log.info("任务不满足自动创建月/天区间，跳过创建：{}",taskTypeEnum);
            return false;
        }

        return true;
    }
}
