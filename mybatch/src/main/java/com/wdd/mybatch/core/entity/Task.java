package com.wdd.mybatch.core.entity;

import com.wdd.mybatch.core.enums.TaskStatusEnum;
import com.wdd.mybatch.core.enums.TaskTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Task {

    private Long id;

    /**
     * 任务类型
     */
    private TaskTypeEnum taskType;

    /**
     * 业务时间
     */
    private LocalDateTime businessTime;

    /**
     * 业务编号
     */
    private String businessId;

    /**
     * 任务状态
     */
    private TaskStatusEnum taskStatus;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 总条数
     */
    private Long totalCnt;

    /**
     * 成功条数
     */
    private Long successCnt;

    /**
     * 失败条数
     */
    private Long failCnt;

    /**
     * 跳过条数
     */
    private Long skipCnt;

    /**
     * 期望条数
     */
    private Long expectTotalCnt;

    /**
     * 任务输入数据
     */
    private String taskData;

    /**
     * 初始
     * @param id
     */
    public void init(long id){
        this.id=id;
        taskStatus=TaskStatusEnum.INIT;
    }

    /**
     * 开始
     */
    public void start(){
        taskStatus=TaskStatusEnum.SLICE_RUNNING;
        if(startTime == null){
            startTime=LocalDateTime.now();
        }
    }

    /**
     * 任务结束
     */
    public void finish(long failSliceCnt, long successCnt,long failCnt,long skipCnt){
        taskStatus=failSliceCnt>0?TaskStatusEnum.FAIL:TaskStatusEnum.SUCCESS;
        this.successCnt=successCnt;
        this.failCnt=failCnt;
        this.skipCnt=skipCnt;
        totalCnt=successCnt+failCnt+skipCnt;
        endTime=LocalDateTime.now();
    }

    /**
     * 任务失败
     */
    public void fail(){
        taskStatus =TaskStatusEnum.FAIL;
        totalCnt=successCnt+failCnt+skipCnt;
        endTime=LocalDateTime.now();
    }

    public void pause(){
        taskStatus=TaskStatusEnum.PAUSE;
    }

    public boolean isPause(){
        return taskStatus ==TaskStatusEnum.PAUSE;
    }

    public boolean isFinal(){
        return taskStatus ==TaskStatusEnum.SUCCESS||taskStatus ==TaskStatusEnum.FAIL;
    }
}
