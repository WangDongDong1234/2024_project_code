package com.wdd.mybatch.core.enums;

import lombok.Getter;

@Getter
public enum TaskSliceStatusEnum {
    /**
     * 初始
     */
    INIT,
    /**
     * 执行中
     */
    RUNNING,
    /**
     * 继续执行
     */
    CONTINUE,
    /**
     * 成功
     */
    SUCCESS,
    /**
     * 失败
     */
    FAIL
}
