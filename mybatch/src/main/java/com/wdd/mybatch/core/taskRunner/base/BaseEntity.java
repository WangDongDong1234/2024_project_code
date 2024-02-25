package com.wdd.mybatch.core.taskRunner.base;

public interface BaseEntity {

    /**
     * id
     *
     * 分页查询时会将该值赋值给nextMinId
     * @return
     */
    long getId();
}
