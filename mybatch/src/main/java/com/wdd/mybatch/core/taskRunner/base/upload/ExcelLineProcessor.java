package com.wdd.mybatch.core.taskRunner.base.upload;

import java.util.List;

public interface ExcelLineProcessor {

    /**
     * 读取数据成功回调
     * @param businessId
     * @param processProgress
     */
    void readExcelSuccess(long businessId, List<ExcelTempLine> lines, ExcelProcessProgress processProgress);

    /**
     * 读取出错回掉
     * @param businessId
     * @param e
     */
    void readExcelFail(long businessId, Throwable e);
}
