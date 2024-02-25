package com.wdd.mybatch.core.factory;

import com.wdd.mybatch.core.common.utils.ExceptionUtil;
import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.entity.TaskSlice;
import com.wdd.mybatch.core.enums.TaskTypeEnum;
import com.wdd.mybatch.core.taskRunner.TaskRunner;
import com.wdd.mybatch.core.vo.TaskSliceProgress;
import com.wdd.mybatch.core.vo.TaskValidateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class TaskRunnerFactory implements InitializingBean {

    @Autowired
    private List<TaskRunner> taskRunnerList;
    private final Map<TaskTypeEnum, TaskRunner> taskRunnerMap = new ConcurrentHashMap<>();
    @Override
    public void afterPropertiesSet() throws Exception {
        for(TaskRunner taskRunner: taskRunnerList){
            taskRunnerMap.put(taskRunner.support(),taskRunner);
            log.info("注册任务处理器：{}",taskRunner.support());
        }
    }

    /**
     * 任务提交前校验
     *
     * @param newTask
     * @param result
     */
    public void validateTask(Task newTask, TaskValidateResult result) {
        taskRunnerMap.get(newTask.getTaskType()).validateTask(newTask, result);
    }

    /**
     * 创建任务
     *
     * @param businessTime
     * @param taskType
     * @return
     */
    public List<Task> createTask(LocalDateTime businessTime, TaskTypeEnum taskType) {
        try {
            return taskRunnerMap.get(taskType).createTask(businessTime);
        } catch (Exception e) {
            log.error("任务创建出错:{}, {}", taskType, ExceptionUtil.getStackTrace(e));
            throw e;
        }
    }

    /**
     * 任务信息补充，修改会自动保存
     *
     * @param task
     */
    public void decorateTask(Task task) {
        taskRunnerMap.get(task.getTaskType()).decorateTask(task);
    }

    /**
     * 任务分片
     *
     * @param task
     * @return
     */
    public List<TaskSlice> splitTask(Task task) {
        return taskRunnerMap.get(task.getTaskType()).splitTask(task);
    }

    /**
     * 执行分片
     *
     * @param task
     * @param taskSlice
     */
    public TaskSliceProgress runSlice(Task task, TaskSlice taskSlice) {
        return taskRunnerMap.get(task.getTaskType()).runSlice(task, taskSlice);
    }

    /**
     * 合并分片
     *
     * @param task
     */
    public void mergeSplice(Task task) {
        taskRunnerMap.get(task.getTaskType()).mergeSlice(task);
    }

    /**
     * 成功结束
     *
     * @param task
     */
    public void success(Task task) {
        taskRunnerMap.get(task.getTaskType()).success(task);
    }

    /**
     * 失败结束
     *
     * @param task
     * @param failReason
     */
    public void fail(Task task, String failReason) {
        taskRunnerMap.get(task.getTaskType()).fail(task, failReason);
    }

    /**
     * 异步刷新分片进度
     *
     * @param taskSlice
     * @param sliceProgress
     * @return
     */
    public TaskSlice asyncRefreshSliceProgress(TaskSlice taskSlice,
                                               TaskSliceProgress sliceProgress) {
        return taskRunnerMap.get(taskSlice.getTask().getTaskType())
                .asyncRefreshSliceProgress(taskSlice, sliceProgress);
    }

    /**
     * 任务恢复调度
     *
     * @param task
     */
    public void resume(Task task) {
        taskRunnerMap.get(task.getTaskType()).resume(task);
    }

    /**
     * 审批并恢复调度
     *
     * @param task
     * @param approveResult
     * @param approveMis
     */
    public void approveAndResume(Task task, boolean approveResult, String approveMis) {
        taskRunnerMap.get(task.getTaskType()).approveAndResume(task, approveResult, approveMis);
    }

    public void onTaskCreated(Task task, boolean byManual) {
        taskRunnerMap.get(task.getTaskType()).onTaskCreated(task, byManual);
    }
}
