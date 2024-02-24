package com.wdd.mybatch.infrastructure.config.task;

import com.wdd.mybatch.core.config.task.TaskScheduleConfig;
import com.wdd.mybatch.core.enums.TaskTypeEnum;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskScheduleConfigImpl implements TaskScheduleConfig {
    @Override
    public boolean isTaskTypeStopSchedule(TaskTypeEnum taskType) {
        return false;
    }

    @Override
    public int getTaskCreateStartDay(TaskTypeEnum taskType) {
        return 0;
    }

    @Override
    public int getSingleExecuteTaskCnt(TaskTypeEnum taskType) {
        return 0;
    }

    @Override
    public int getSingleSliceExecuteCnt(TaskTypeEnum taskType) {
        return 0;
    }

    @Override
    public int getMaxSliceExecuteCntPerTask(TaskTypeEnum taskType) {
        return 0;
    }

    @Override
    public int getTaskScheduleStartDay(TaskTypeEnum taskType) {
        return 0;
    }

    @Override
    public boolean inTaskCreateTimeRange(TaskTypeEnum taskType) {
        return false;
    }

    @Override
    public boolean matchTaskCreateIntervalMinutes(TaskTypeEnum taskType) {
        return false;
    }

    @Override
    public boolean inTaskCreateWeekDayRange(TaskTypeEnum taskType) {
        return false;
    }

    @Override
    public boolean inTaskCreateMonthDayRange(TaskTypeEnum taskType) {
        return false;
    }

    @Override
    public List<String> successNotifyMisList(TaskTypeEnum taskType) {
        return null;
    }

    @Override
    public List<String> failNotifyMisList(TaskTypeEnum taskType) {
        return null;
    }

    @Override
    public boolean matchTaskExecuteIntervalMinutes(TaskTypeEnum taskType) {
        return false;
    }

    @Override
    public int getTaskSliceTimeoutMinutes(TaskTypeEnum taskType) {
        return 0;
    }

    @Override
    public int getSliceBatchInsertSize(TaskTypeEnum taskType) {
        return 0;
    }

    @Override
    public boolean inTaskExecuteTimeRange(TaskTypeEnum taskType) {
        return false;
    }

    @Override
    public int getManualTaskMaxLineCnt(TaskTypeEnum taskType) {
        return 0;
    }

    @Override
    public String getManualTaskCreateTimeRange(TaskTypeEnum taskType) {
        return null;
    }

    @Override
    public boolean inManualTaskCreateTimeRange(TaskTypeEnum taskType) {
        return false;
    }

    @Override
    public boolean inTaskSliceFastSchedule(TaskTypeEnum taskType) {
        return false;
    }
}
