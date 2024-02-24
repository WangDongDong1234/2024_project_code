package com.wdd.mybatch.core.taskRunner.base;

import com.wdd.mybatch.core.enums.TaskStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class BaseTaskData {

    /**
     * 暂停恢复后状态
     */
    private TaskStatusEnum resumeStatus;

    /**
     * 审批结果
     */
    private Boolean approveResult;

    /**
     * 审批人
     */
    private String approveMis;
}
