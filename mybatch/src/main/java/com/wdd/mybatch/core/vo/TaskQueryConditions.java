package com.wdd.mybatch.core.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskQueryConditions {
    /**
     * mis
     */
    private String operator;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 任务状态
     */
    private String taskStatus;

    /**
     * 业务时间开始
     */
    private LocalDateTime businessTimeStart;

    /**
     * 业务时间结束
     */
    private LocalDateTime businessTimeEnd;

    /**
     * 分页参数
     */
    private int start;

    /**
     * 分页参数
     */
    private int limit;
}
