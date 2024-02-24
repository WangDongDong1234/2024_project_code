package com.wdd.mybatch.core.vo;

import lombok.Data;

@Data
public class TaskSliceProgress {

    private long taskId;

    private long taskSliceId;

    private long successCnt;

    private long failCnt;

    private long skipCnt;

    private long nextMinId;

    private int pageSize;

    public long getTotalCnt(){
        return successCnt+failCnt+skipCnt;
    }

    public boolean isFullRound(){
        return getTotalCnt()==pageSize;
    }


}
