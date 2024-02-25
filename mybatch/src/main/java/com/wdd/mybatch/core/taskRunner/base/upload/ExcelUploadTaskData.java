package com.wdd.mybatch.core.taskRunner.base.upload;

import com.wdd.mybatch.core.taskRunner.base.BaseTaskData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ExcelUploadTaskData  extends BaseTaskData {

    /**
     * excel的s3文件地址
     */
    private String excelS3ObjectName;

    /**
     * excel的原始文件名称
     */
    private String originalFileName;

    /**
     * zip的s3文件地址
     */
    private String zipS3ObjectName;

    /**
     * zip的原始文件名称
     */
    private String zipOriginalFileName;

    private String extInfo;

}
