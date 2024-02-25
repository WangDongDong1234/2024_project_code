package com.wdd.mybatch.core.taskexecutor.category;

import com.wdd.mybatch.core.common.utils.ExceptionUtil;
import com.wdd.mybatch.core.common.utils.UuidUtils;
import com.wdd.mybatch.core.config.task.TaskScheduleConfig;
import com.wdd.mybatch.core.consts.Consts;
import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.enums.TaskStatusEnum;
import com.wdd.mybatch.core.enums.TaskTypeEnum;
import com.wdd.mybatch.core.factory.TaskRunnerFactory;
import com.wdd.mybatch.core.repository.TaskRepository;
import com.wdd.mybatch.core.taskexecutor.BaseTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;


@Slf4j
@Component
public class TaskCreateExecutor extends BaseTaskExecutor {

    @Autowired
    @Qualifier(Consts.ASYNC_TAK_POOL)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private TaskScheduleConfig taskScheduleConfig;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskRunnerFactory taskRunnerFactory;

    @Override
    protected void doExecute(Task task) {
        int startDay =taskScheduleConfig.getTaskCreateStartDay(task.getTaskType());
        LocalDateTime today =LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        for(int i =startDay;i<=0;i++){
            LocalDateTime businessTime =today.plusDays(i);
            log.info("尝试异步创建人物:{},{}",task.getTaskType(),businessTime);

            threadPoolTaskExecutor.execute(()->generateTaskOneDay(businessTime,task.getTaskType()));
        }

    }

    private void generateTaskOneDay(LocalDateTime businessTime, TaskTypeEnum taskType) {
        try {
            List<Task> taskList = taskRunnerFactory.createTask(businessTime, taskType);
            if (taskList == null) {
                return;
            }

            log.info("尝试创建任务数量：{}，{}，{}", taskList.size(), businessTime, taskType);
            Iterator<Task> iterator = taskList.iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                Task exist = taskRepository.queryByTimeTypeBizId(task.getBusinessTime(),
                        task.getTaskType(), task.getBusinessId());
                if (exist == null) {
                    log.info("任务不存在新创建：{}", task);
                    task.init(UuidUtils.getId());
                    taskRepository.insert(task);


                    String s = String.format("【%s】已创建\ntaskId=%s，bizId=%s",
                            task.getTaskType().getTaskName(), task.getId(), task.getBusinessId());

                    taskRunnerFactory.onTaskCreated(task, false);
                } else {
                    log.info("任务已存在跳过：{}", exist);
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            log.error("generateTaskOneDay 异常:{}", ExceptionUtil.getStackTrace(e));
        }
    }

    @Override
    public TaskStatusEnum support() {
        return TaskStatusEnum.CREATE;
    }
}
