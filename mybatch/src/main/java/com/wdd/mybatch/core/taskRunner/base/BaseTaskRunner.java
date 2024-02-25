package com.wdd.mybatch.core.taskRunner.base;

import com.alibaba.fastjson.JSON;
import com.wdd.mybatch.core.common.service.ParamClassResolver;
import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.entity.TaskSlice;
import com.wdd.mybatch.core.enums.MessageTypeEnum;
import com.wdd.mybatch.core.enums.TaskSliceStatusEnum;
import com.wdd.mybatch.core.enums.TaskStatusEnum;
import com.wdd.mybatch.core.message.MessagePublisher;
import com.wdd.mybatch.core.repository.TaskRepository;
import com.wdd.mybatch.core.repository.TaskSliceRepository;
import com.wdd.mybatch.core.taskRunner.TaskRunner;
import com.wdd.mybatch.core.vo.TaskSliceProgress;
import com.wdd.mybatch.core.vo.TaskValidateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
public abstract class BaseTaskRunner<T extends BaseTaskData,S extends BaseTaskSliceData> implements TaskRunner {

    @Autowired
    private ParamClassResolver paramClassResolver;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskSliceRepository taskSliceRepository;

    @Autowired
    private MessagePublisher messagePublisher;


    protected String toJson(Object object){
        return JSON.toJSONString(object);
    }

    protected S sliceDataFromJson(String sliceData){
        return (S)JSON.parseObject(sliceData,paramClassResolver.resolveClassName(getClass(),1));
    }

    protected T taskDataFromJson(String taskData){
        return (T)JSON.parseObject(taskData,paramClassResolver.resolveClassName(getClass(),0));
    }

    /**
     * 计算单个任务的预期行数
     * @param task
     * @param taskData
     * @return
     */
    protected long queryExpectTotalCnt(Task task, T taskData){
        return 0;
    }

    @Override
    public void validateTask(Task newTask, TaskValidateResult result) {
        //TODO  任务校验暂时不实现
    }

    @Override
    public void decorateTask(Task task) {
        if(doDecorateTask()){
            log.info("任务预处理发生修改，更新：{}",task);
            taskRepository.updateById(task);
        }
    }

    protected boolean doDecorateTask(){
        return false;
    }

    @Override
    public void onTaskCreated(Task task, boolean byManual) {

    }

    @Override
    public TaskSliceProgress runSlice(Task task, TaskSlice taskSlice) {
        // 可以加个限流
        log.info("runSlice,sliceId={}",taskSlice.getId());
        fixSliceDataBeforeRun(taskSlice,sliceDataFromJson(taskSlice.getSliceData()));
        return doRunSlice(task,taskSlice);
    }

    protected  TaskSliceProgress doRunSlice(Task task, TaskSlice taskSlice){
        // 分片发送消息，多机执行
        messagePublisher.sendMessage(MessageTypeEnum.SCHEDULE_TASK_SLICE_MESSAGE,taskSlice);
        log.info("分片消息发送，taskSlice{}",taskSlice);
        return null;
    }

    /**
     * 执行前修改参数
     * @param taskSlice
     * @param sliceData
     */
    private void fixSliceDataBeforeRun(TaskSlice taskSlice, S sliceData){
        doFixSliceDataBeforeRun(taskSlice,sliceData);
        taskSlice.setSliceData(toJson(sliceData));
    }

    /**
     * 执行前按需修改参数
     * @param taskSlice
     * @param sliceData
     */
    protected void doFixSliceDataBeforeRun(TaskSlice taskSlice, S sliceData){

    }

    @Override
    public void mergeSlice(Task task) {
        log.info("mergeSlice:taskId={}",task.getId());
        doMergeSlice(task);
    }

    /**
     * 分片合并
     * @param task
     */
    private void doMergeSlice(Task task) {
    }

    @Override
    public void beforeComplete(Task task) {
        log.info("beforeComplete:taskId={}",task.getId());
    }

    @Override
    public void success(Task task) {
        String s = String.format(
                " 任务执行报告：\n【%s】执行【成功】\ntaskId=%s，bizId=%s\n成功 / 失败 / 跳过条数：【%s / %s / %s】，实际 / 预期总条数：【%s / " +
                        "%s】，总时长：【%s分钟】",
                task.getTaskType().getTaskName(),
                task.getId(), task.getBusinessId(), task.getSuccessCnt(), task.getFailCnt(),
                task.getSkipCnt(), task.getTotalCnt(), task.getExpectTotalCnt(),
                task.getStartTime().until(task.getEndTime(), ChronoUnit.MINUTES));
        log.info(s);

        // 此处可以做一些消息通知
    }

    @Override
    public void fail(Task task, String failReason) {
        String s = String.format(
                " 任务执行报告：\n！！！【%s】执行【失败】！！！\ntaskId=%s，bizId=%s\n成功 / 失败 / 跳过条数：【%s / %s / %s】，实际 / 预期总条数: 【%s / " +
                        "%s】，总时长：【%s分钟】\n原因：【%s】",
                task.getTaskType().getTaskName(), task.getId(), task.getBusinessId(),
                task.getSuccessCnt(), task.getFailCnt(), task.getSkipCnt(), task.getTotalCnt(),
                task.getExpectTotalCnt(),
                task.getStartTime().until(task.getEndTime(), ChronoUnit.MINUTES), failReason);
        log.info(s);
    }

    @Override
    public TaskSlice asyncRefreshSliceProgress(TaskSlice taskSlice, TaskSliceProgress sliceProgress) {

        // 事务、加锁更新，防止覆盖
        TaskSlice slice = taskSliceRepository.lockById(taskSlice.getId());
        log.info("更新进度前slice: {}", slice);
        S sliceData = sliceDataFromJson(slice.getSliceData());

        sliceData.setNextMinId(sliceProgress.getNextMinId());
        if (sliceProgress.getFailCnt() >= sliceData.getFailCntThreshold()) {
            slice.setSliceStatus(TaskSliceStatusEnum.FAIL);
        }

        slice.setSliceData(toJson(sliceData));

        slice.asyncRefreshProgress(sliceProgress);

        long cnt = taskSliceRepository.updateById(slice);
        log.info("更新进度后slice: {}，cnt={}", slice, cnt);


        return slice;
    }


    /**
     * 暂停任务调度
     *
     * @param task
     */
    protected void pause(Task task, TaskStatusEnum resumeStatus) {
        task.pause();
        T taskData = taskDataFromJson(task.getTaskData());
        taskData.setResumeStatus(resumeStatus);
        task.setTaskData(toJson(taskData));
        taskRepository.updateById(task);
        log.info("任务暂停,task={}", task);
    }

    /**
     * 恢复任务调度
     *
     * @param task
     */
    @Override
    public void resume(Task task) {
        T taskData = taskDataFromJson(task.getTaskData());

        task.setTaskStatus(taskData.getResumeStatus());
        taskData.setResumeStatus(null);
        task.setTaskData(toJson(taskData));

        taskRepository.updateById(task);
        log.info("任务恢复,task={}", task);
    }

    @Override
    public void approveAndResume(Task task, boolean approveResult, String approveMis) {
        T taskData = taskDataFromJson(task.getTaskData());
        task.setTaskStatus(approveResult ? taskData.getResumeStatus() : TaskStatusEnum.FAIL);
        taskData.setResumeStatus(null);
        taskData.setApproveResult(approveResult);
        taskData.setApproveMis(approveMis);
        task.setTaskData(toJson(taskData));
        task.setStartTime(LocalDateTime.now());
        taskRepository.updateById(task);
        log.info("任务审批后恢复，task={}", task);
    }
}
