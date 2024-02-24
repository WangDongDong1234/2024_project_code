package com.wdd.mybatch.core.taskRunner;

import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.entity.TaskSlice;
import com.wdd.mybatch.core.enums.TaskTypeEnum;
import com.wdd.mybatch.core.vo.TaskSliceProgress;
import com.wdd.mybatch.core.vo.TaskValidateResult;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRunner {

    /**
     * 任务类型
     * @return
     */
    TaskTypeEnum support();

    /**
     * 校验任务
     * @param newTask
     * @param result
     */
    void validateTask(Task newTask, TaskValidateResult result);

    /**
     * 创建任务
     * @param businessTime
     * @return
     */
    List<Task> createTask(LocalDateTime businessTime);

    /**
     * 任务分片
     * @param task
     * @return
     */
    List<TaskSlice> splitTask(Task task);

    /**
     * 执行分片
     * @param task
     * @param taskSlice
     * @return
     */
    TaskSliceProgress runSlice(Task task, TaskSlice taskSlice);

    /**
     * 任务合并
     * @param task
     */
    void mergeSlice(Task task);

    /**
     * 结束前处理
     * @param task
     */
    void beforeComplete(Task task);

    /**
     * 任务成功
     * @param task
     */
    void success(Task task);

    /**
     * 任务失败
     * @param task
     * @param failReason
     */
    void fail(Task task,String failReason);

    /**
     * 异步刷新分片进度
     * @param taskSlice
     * @param sliceProgress
     * @return
     */
    TaskSlice asyncRefreshSliceProgress(TaskSlice taskSlice,TaskSliceProgress sliceProgress);

    /**
     * 恢复任务调度
     * @param task
     */
    void resume(Task task);

    /**
     * 审批并恢复调度
     * @param task
     * @param approveResult
     * @param approveMis
     */
    void approveAndResume(Task task,boolean approveResult,String approveMis);

    /**
     * 任务预处理，修改会自动保存
     * @param task
     */
    void decorateTask(Task task);

    /**
     * 任务已创建事件
     * @param task
     * @param byManual
     */
    void onTaskCreated(Task task,boolean byManual);

}
