package com.wdd.mybatch.core.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
public class ExceptionUtil {
    /**
     * 把异常对象转化为String进行打印
     */
    public static String getStackTrace(Throwable throwable) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw, true);
            throwable.printStackTrace(pw);
            return sw.getBuffer().toString();
        } catch (Exception e) {
            log.error("异常转字符串失败:{}", e.getMessage());
            return e.getMessage();
        }
    }

}
