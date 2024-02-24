package com.wdd.mybatch.core.po;

import java.time.LocalDateTime;

public class TaskPO {
    private Long id;

    private String taskType;

    private LocalDateTime businessTime;

    private String businessId;

    private String taskStatus;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String operator;

    private Long totalCnt;

    private Long successCnt;

    private Long failCnt;

    private Long skipCnt;

    private Long expectTotalCnt;

    private String taskData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType == null ? null : taskType.trim();
    }

    public LocalDateTime getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(LocalDateTime businessTime) {
        this.businessTime = businessTime;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId == null ? null : businessId.trim();
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus == null ? null : taskStatus.trim();
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Long getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(Long totalCnt) {
        this.totalCnt = totalCnt;
    }

    public Long getSuccessCnt() {
        return successCnt;
    }

    public void setSuccessCnt(Long successCnt) {
        this.successCnt = successCnt;
    }

    public Long getFailCnt() {
        return failCnt;
    }

    public void setFailCnt(Long failCnt) {
        this.failCnt = failCnt;
    }

    public Long getSkipCnt() {
        return skipCnt;
    }

    public void setSkipCnt(Long skipCnt) {
        this.skipCnt = skipCnt;
    }

    public Long getExpectTotalCnt() {
        return expectTotalCnt;
    }

    public void setExpectTotalCnt(Long expectTotalCnt) {
        this.expectTotalCnt = expectTotalCnt;
    }

    public String getTaskData() {
        return taskData;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData == null ? null : taskData.trim();
    }
}