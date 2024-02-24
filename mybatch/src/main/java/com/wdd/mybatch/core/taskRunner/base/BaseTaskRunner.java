package com.wdd.mybatch.core.taskRunner.base;

import com.alibaba.fastjson.JSON;
import com.wdd.mybatch.core.common.service.ParamClassResolver;
import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.entity.TaskSlice;
import com.wdd.mybatch.core.enums.MessageTypeEnum;
import com.wdd.mybatch.core.message.MessagePublisher;
import com.wdd.mybatch.core.repository.TaskRepository;
import com.wdd.mybatch.core.taskRunner.TaskRunner;
import com.wdd.mybatch.core.vo.TaskSliceProgress;
import com.wdd.mybatch.core.vo.TaskValidateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class BaseTaskRunner<T extends BaseTaskData,S extends BaseTaskSliceData> implements TaskRunner {

    @Autowired
    private ParamClassResolver paramClassResolver;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MessagePublisher messagePublisher;


    protected String toJson(Object object){
        return JSON.toJSONString(object);
    }

    protected S sliceDataFromJson(String sliceData){
        return (S)JSON.parseObject(sliceData,paramClassResolver.resolveClassName(getClass(),1));
    }

    protected T taskDateFromJson(String taskData){
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
        //TODO
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
}
