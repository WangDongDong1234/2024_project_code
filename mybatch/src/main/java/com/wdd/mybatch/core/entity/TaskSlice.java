package com.wdd.mybatch.core.entity;

import com.wdd.mybatch.core.enums.TaskSliceStatusEnum;
import com.wdd.mybatch.core.vo.TaskSliceProgress;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Data
public class TaskSlice {
    private Long id;

    /**
     * 任务
     */
    private Task task;

    /**
     * 业务编号
     */
    private String businessId;

    /**
     * 分片状态
     */
    private TaskSliceStatusEnum sliceStatus;

    /**
     * 分片数据
     */
    private String sliceData;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

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
     * 轮询次数
     */
    private Integer roundNumber;

    /**
     * 失败原因
     */
    private String failReason;

    /**
     * 初始
     * @param id
     * @param task
     */
    public void init(long id,Task task){
        this.id=id;
        this.task=task;
        sliceStatus=TaskSliceStatusEnum.INIT;
    }

    /**
     * 开始
     */
    public void start(){
        if(startTime == null){
            startTime =LocalDateTime.now();
        }
        sliceStatus=TaskSliceStatusEnum.RUNNING;
    }

    /**
     * 同步结束，更新进度
     * @param taskSliceProgress
     */
    public void syncRefreshProgress(TaskSliceProgress taskSliceProgress){
       successCnt=taskSliceProgress.getSuccessCnt();
       failCnt=taskSliceProgress.getFailCnt();
       skipCnt=taskSliceProgress.getSkipCnt();
       ++roundNumber;
       finish();
    }

    /**
     * 结束
     */
    public void finish(){
        sliceStatus=failCnt>0?TaskSliceStatusEnum.FAIL:TaskSliceStatusEnum.SUCCESS;
        if(sliceStatus==TaskSliceStatusEnum.FAIL){
            failReason="存在失败的记录，数量:"+failCnt;
        }
        totalCnt =successCnt+failCnt+skipCnt;
        endTime=LocalDateTime.now();
    }

    /**
     * 是否成功
     * @return
     */
    public boolean isFinishAndHasSucessRecord(){
        return sliceStatus==TaskSliceStatusEnum.SUCCESS
                ||(sliceStatus==TaskSliceStatusEnum.FAIL&&successCnt>0);
    }


    /**
     * 失败结束
     * @param message
     */
    public void fail(String message){
        failReason = message;
        endTime=LocalDateTime.now();
        sliceStatus=TaskSliceStatusEnum.FAIL;
    }

    public void asyncRefreshProcess(TaskSliceProgress sliceProgress){
        successCnt+=sliceProgress.getSuccessCnt();
        failCnt+=sliceProgress.getFailCnt();
        skipCnt+=sliceProgress.getSkipCnt();
        ++roundNumber;
        if(sliceProgress.getTotalCnt()<sliceProgress.getPageSize()&&!task.getTaskType().isInfinity()){
            // 结束了
            finish();
        }else{
            totalCnt=successCnt+failCnt+skipCnt;
            switch (sliceStatus){
                case RUNNING:
                    sliceStatus=TaskSliceStatusEnum.CONTINUE;
                    break;
                case FAIL:
                    fail("失败数据条数超出阈值："+failCnt);
                    break;
                default:
                    log.error("不应该走到这里");
            }
        }
    }

}
