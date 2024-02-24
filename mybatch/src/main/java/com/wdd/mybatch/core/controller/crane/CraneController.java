package com.wdd.mybatch.core.controller.crane;


import com.wdd.mybatch.core.common.dtos.ResultEntity;
import com.wdd.mybatch.core.service.ScheduleService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class CraneController {

    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation("创建任务")
    @ResponseBody
    @RequestMapping("/scheduleCreate")
    public ResultEntity sheduleCreate(){
        log.info("batch.schedule.task.create");
        scheduleService.createTask();
        return ResultEntity.successWithoutData();

    }
}
