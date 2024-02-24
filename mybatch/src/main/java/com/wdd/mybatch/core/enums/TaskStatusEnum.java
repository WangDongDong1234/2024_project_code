package com.wdd.mybatch.core.enums;

public enum TaskStatusEnum {

    /**
     * 创建
     */
    CREATE,
    /**
     * 初始
     */
    INIT,
    /**
     * 分片执行中
     */
    SLICE_RUNNING,
    /**
     * 分片执行完成合并
     */
    SLICE_MERGE,
    /**
     * 任务结束前
     */
    BEFORE_COMPLETE,
    /**
     * 成功结束
     */
    SUCCESS,
    /**
     * 失败结束
     */
    FAIL,
    /**
     * 暂停
     */
    PAUSE
}
