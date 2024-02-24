package com.wdd.mybatch.core.enums;

public enum MessageTypeEnum {

    SCHEDULE_TASK_SLICE_MESSAGE("调度任务分片执行消息","schedule.task.slice.execute");

    private String messageName;

    private String topic;

    MessageTypeEnum(String messageName, String topic) {
        this.messageName = messageName;
        this.topic = topic;
    }
}
