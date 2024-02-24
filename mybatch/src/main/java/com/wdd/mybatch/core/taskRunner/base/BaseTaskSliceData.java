package com.wdd.mybatch.core.taskRunner.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class BaseTaskSliceData {

    /**
     * id最小偏移量，作为查询条件【>id】
     */
    private long nextMinId=-1;

    /**
     * 单次处理数量
     */
    private int pageSize=500;

    /**
     * 失败处理阈值，超过后不再继续处理
     */
    private long failCntThreshold=10;
}
