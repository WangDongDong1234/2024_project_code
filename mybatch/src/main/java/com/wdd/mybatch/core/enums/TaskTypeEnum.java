package com.wdd.mybatch.core.enums;

import lombok.Getter;

@Getter
public enum TaskTypeEnum {
    UPLOAD("上传任务",false)
    ;

    private final String taskName;

    private final boolean infinity;

    TaskTypeEnum(String taskName, boolean infinity) {
        this.taskName = taskName;
        this.infinity = infinity;
    }
}
