package com.wdd.mybatch.core.po;

import java.time.LocalDateTime;

public class TaskSlicePO {
    private Long id;

    private Long taskId;

    private String businessId;

    private String sliceStatus;

    private String sliceData;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long totalCnt;

    private Long successCnt;

    private Long failCnt;

    private Long skipCnt;

    private Integer roundNumber;

    private String failReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId == null ? null : businessId.trim();
    }

    public String getSliceStatus() {
        return sliceStatus;
    }

    public void setSliceStatus(String sliceStatus) {
        this.sliceStatus = sliceStatus == null ? null : sliceStatus.trim();
    }

    public String getSliceData() {
        return sliceData;
    }

    public void setSliceData(String sliceData) {
        this.sliceData = sliceData == null ? null : sliceData.trim();
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

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }
}