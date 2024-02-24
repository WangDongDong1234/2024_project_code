package com.wdd.mybatch.core.controller.test;

import com.wdd.mybatch.core.mapper.ExcelLineMapper;
import com.wdd.mybatch.core.po.ExcelLinePO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "表查询数据测试")
public class TableControllerTest {

    @Autowired
    private ExcelLineMapper excelLineMapper;


    /**
     * http://localhost:8080/swagger-ui.html
     */
    @ApiOperation("")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() {
        log.info("TestController-测试接口运行开始");
        ExcelLinePO po = excelLineMapper.selectByPrimaryKey(1L);

        log.info("TestController-测试接口运行结束:po{}",po);
    }
}
