package com.wdd.mybatch.core.taskRunner.base.upload;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class ExcelTempLine extends JSONObject {

    /**
     * 是否读取成功
     */
    @JSONField(serialize = false)
    private boolean readSuccess = true;

    /**
     * 各字段错误描述，excel表头 ->错误说明
     */
    @JSONField(serialize = false)
    private Map<String,String> errorMap;

    public void putError(String columnName,String errorMessage){
        readSuccess=false;
        if(errorMap==null){
            errorMap = new HashMap<>();
        }
        errorMap.put(columnName,errorMessage);
    }
}
