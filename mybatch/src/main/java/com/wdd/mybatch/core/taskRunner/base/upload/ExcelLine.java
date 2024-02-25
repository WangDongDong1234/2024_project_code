package com.wdd.mybatch.core.taskRunner.base.upload;

import com.wdd.mybatch.core.taskRunner.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ExcelLine implements BaseEntity {
    /**
     * ID
     */
    private long id;

    /**
     * 行编号
     */
    private int lineNo;

    /**
     * 分片号
     */
    private String shardValue;

}
