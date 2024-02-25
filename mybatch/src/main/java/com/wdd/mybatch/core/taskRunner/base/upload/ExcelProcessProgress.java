package com.wdd.mybatch.core.taskRunner.base.upload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

@Getter
@Setter
@ToString
public class ExcelProcessProgress {
    /**
     * 表总行数
     */
    private AtomicLong totalCnt = new AtomicLong();

    /**
     * 处理成功行数
     */
    private LongAdder successCnt = new LongAdder();

    /**
     * 处理失败行数
     */
    private LongAdder failCnt = new LongAdder();

    /**
     * 表是否读取完成
     */
    private volatile boolean eof;

    public void increaseLines(int size) {
        totalCnt.addAndGet(size);
    }

    public void increaseFail() {
        failCnt.increment();
    }

    public void increaseSuccess() {
        successCnt.increment();
    }

    public boolean isFinish() {
        return successCnt.longValue() + failCnt.longValue() == totalCnt.longValue();
    }
}
