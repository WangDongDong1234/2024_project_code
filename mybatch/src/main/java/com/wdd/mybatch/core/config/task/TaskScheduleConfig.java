package com.wdd.mybatch.core.config.task;

import com.wdd.mybatch.core.enums.TaskTypeEnum;

import java.util.List;

public interface TaskScheduleConfig {

    /**
     * 任务是否暂停调度，如果暂停，则不创建任务
     * @param taskType
     * @return
     */
    boolean isTaskTypeStopSchedule(TaskTypeEnum taskType);

    /**
     * 任务创建往往前的天数
     * @param taskType
     * @return
     */
    int getTaskCreateStartDay(TaskTypeEnum taskType);

    /**
     * 单次任务调度的数量
     * @param taskType
     * @return
     */
    int getSingleExecuteTaskCnt(TaskTypeEnum taskType);

    /**
     * 单次分片调度的数量
     * @param taskType
     * @return
     */
    int getSingleSliceExecuteCnt(TaskTypeEnum taskType);

    /**
     * 单个任务最多调度分片数量
     * @param taskType
     * @return
     */
    int getMaxSliceExecuteCntPerTask(TaskTypeEnum taskType);

    /**
     * 任务调度往前的任务天数
     * @param taskType
     * @return
     */
    int getTaskScheduleStartDay(TaskTypeEnum taskType);

    /**
     * 自动创建任务是否在创建时间区间内
     * @param taskType
     * @return
     */
    boolean inTaskCreateTimeRange(TaskTypeEnum taskType);

    /**
     * 自动创建任务，创建时间间隔. 默认分钟%1=1可以创建，即任何时刻都可创建
     * @param taskType
     * @return
     */
    boolean matchTaskCreateIntervalMinutes(TaskTypeEnum taskType);

    /**
     * 自动创建任务是否满足周/天区间
     * @param taskType
     * @return
     */
    boolean inTaskCreateWeekDayRange(TaskTypeEnum taskType);

    /**
     * 自动创建任务是否满足月/天区间
     * @param taskType
     * @return
     */
    boolean inTaskCreateMonthDayRange(TaskTypeEnum taskType);

    /**
     * 成功通知名单
     * @param taskType
     * @return
     */
    List<String> successNotifyMisList(TaskTypeEnum taskType);

    /**
     * 任务通知名单
     * @param taskType
     * @return
     */
    List<String> failNotifyMisList(TaskTypeEnum taskType);

    /**
     * 是否满足时间间隔 ？？
     * @param taskType
     * @return
     */
    boolean matchTaskExecuteIntervalMinutes(TaskTypeEnum taskType);


    /**
     * 分片执行超时时间？？
     * @param taskType
     * @return
     */
    int getTaskSliceTimeoutMinutes(TaskTypeEnum taskType);

    /**
     * 分片批量插入大小???
     * @param taskType
     * @return
     */
    int getSliceBatchInsertSize(TaskTypeEnum taskType);

    /**
     * 任务是否在执行时间区间内
     * @param taskType
     * @return
     */
    boolean inTaskExecuteTimeRange(TaskTypeEnum taskType);

    /**
     * 手工创建任务，最大执行行数
     * @param taskType
     * @return
     */
    int getManualTaskMaxLineCnt(TaskTypeEnum taskType);

    /**
     * 手工创建任务，时间范围
     * @param taskType
     * @return
     */
    String getManualTaskCreateTimeRange(TaskTypeEnum taskType);

    /**
     * 手工触发任务，是否在任务创建时间范围
     * @param taskType
     * @return
     */
    boolean inManualTaskCreateTimeRange(TaskTypeEnum taskType);

    /**
     * 任务分片是否快速调速
     * @param taskType
     * @return
     */
    boolean inTaskSliceFastSchedule(TaskTypeEnum taskType);
}
