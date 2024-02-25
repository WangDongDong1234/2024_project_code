package com.wdd.mybatch.core.taskRunner.base.upload;

import com.wdd.mybatch.core.taskRunner.base.BaseTaskRunner;
import com.wdd.mybatch.core.taskRunner.base.BaseTaskSliceData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseExcelUploadTaskRunner<T extends ExcelUploadTaskData,S extends BaseTaskSliceData,E extends ExcelLine> extends BaseTaskRunner<T,S> implements ExcelLineProcessor {
}
