package com.wdd.mybatch.core.repository.impl;

import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.enums.TaskStatusEnum;
import com.wdd.mybatch.core.enums.TaskTypeEnum;
import com.wdd.mybatch.core.mapper.TaskMapper;
import com.wdd.mybatch.core.po.TaskPO;
import com.wdd.mybatch.core.repository.TaskRepository;
import com.wdd.mybatch.core.vo.PageInfo;
import com.wdd.mybatch.core.vo.TaskQueryConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    @Autowired
    private TaskMapper taskMapper;


    @Override
    public Task queryByTimeTypeBizId(LocalDateTime businessTime, TaskTypeEnum taskType,
                                     String businessId) {
        TaskPO taskPO =  taskMapper.queryByTimeTypeBizId(businessTime, taskType.name(), businessId);
        return convert2Model(taskPO);
    }

    private Task convert2Model(TaskPO taskPO) {
        if (taskPO == null) {
            return null;
        }

        Task task = new Task();
        task.setId(taskPO.getId());
        task.setTaskType(TaskTypeEnum.valueOf(taskPO.getTaskType()));
        task.setBusinessId(taskPO.getBusinessId());
        task.setBusinessTime(taskPO.getBusinessTime());
        task.setTaskStatus(TaskStatusEnum.valueOf(taskPO.getTaskStatus()));
        task.setStartTime(taskPO.getStartTime());
        task.setTaskData(taskPO.getTaskData());
        task.setEndTime(taskPO.getEndTime());
        task.setOperator(taskPO.getOperator());
        task.setTotalCnt(taskPO.getTotalCnt());
        task.setSuccessCnt(taskPO.getSuccessCnt());
        task.setFailCnt(taskPO.getFailCnt());
        task.setSkipCnt(taskPO.getSkipCnt());
        task.setExpectTotalCnt(taskPO.getExpectTotalCnt());
        return task;
    }

    @Override
    public void insert(Task task) {
        TaskPO taskPO = convert2PO(task);
        taskMapper.insert(taskPO);
    }

    private TaskPO convert2PO(Task task) {
        if (task == null) {
            return null;
        }

        TaskPO taskPO = new TaskPO();
        taskPO.setId(task.getId());
        taskPO.setTaskType(task.getTaskType().name());
        taskPO.setBusinessId(task.getBusinessId());
        taskPO.setBusinessTime(task.getBusinessTime());
        taskPO.setTaskStatus(task.getTaskStatus().name());
        taskPO.setTaskData(task.getTaskData());
        taskPO.setStartTime(task.getStartTime());
        taskPO.setEndTime(task.getEndTime());
        taskPO.setOperator(task.getOperator());
        taskPO.setTotalCnt(task.getTotalCnt());
        taskPO.setSuccessCnt(task.getSuccessCnt());
        taskPO.setFailCnt(task.getFailCnt());
        taskPO.setSkipCnt(task.getSkipCnt());
        taskPO.setExpectTotalCnt(task.getExpectTotalCnt());
        return taskPO;
    }

    @Override
    public List<Task> queryByTypeStatus(TaskTypeEnum taskType, TaskStatusEnum[] dispatchStatus,
                                        int cnt, LocalDateTime businessTimeStart, LocalDateTime businessTimeEnd) {
        List<TaskPO> taskPOList =  taskMapper.queryByTypeStatus(taskType.name(),
                Arrays.stream(dispatchStatus).map(Enum::name).collect(Collectors.toList()), cnt,
                businessTimeStart, businessTimeEnd);
        return taskPOList.stream().map(this::convert2Model).collect(Collectors.toList());
    }

    @Override
    public long updateById(Task task) {
        TaskPO taskPO = convert2PO(task);
        return taskMapper.updateById(taskPO);
    }

    @Override
    public Task queryById(long id) {
        TaskPO taskPO =  taskMapper.queryById(id);
        return convert2Model(taskPO);
    }

    @Override
    public Task lockById(long id) {
        TaskPO taskPO = taskMapper.lockById(id);
        return convert2Model(taskPO);
    }

    @Override
    public List<Task> queryByTimeTypeFuzzyBizId(LocalDateTime businessTime, TaskTypeEnum taskType,
                                                String fuzzyProductCode) {
        List<TaskPO> taskPOList =
              taskMapper.queryByTimeTypeFuzzyBizId(businessTime, taskType,
                        fuzzyProductCode);
        return taskPOList.stream().map(this::convert2Model).collect(Collectors.toList());
    }

    @Override
    public List<Task> queryByTypeBizIdTime(TaskTypeEnum taskType, String businessId,
                                           LocalDateTime businessTimeStart, LocalDateTime businessTimeEnd) {
        List<TaskPO> taskPOList = taskMapper.queryByTypeBizIdTime(taskType, businessId, businessTimeStart,
                        businessTimeEnd);
        return taskPOList.stream().map(this::convert2Model).collect(Collectors.toList());
    }

    @Override
    public PageInfo<Task> queryTaskByOperatorAndBizTime(String operator,
                                                        LocalDateTime businessTimeStart, LocalDateTime businessTimeEnd, int pageNo,
                                                        int pageSize) {
        List<TaskPO> taskPOS = taskMapper.queryTaskByOperatorAndBizTime(operator, businessTimeStart,
                businessTimeEnd, (pageNo - 1) * pageSize, pageSize);

        List<Task> tasks = taskPOS.stream().map(this::convert2Model).collect(Collectors.toList());
        PageInfo<Task> pageInfo = new PageInfo<>();
        pageInfo.setList(tasks);
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(pageNo);
        pageInfo.setTotal(tasks.size());
        return pageInfo;
    }

    @Override
    public List<Task> queryTaskList(TaskQueryConditions taskQueryConditions) {
        List<TaskPO> taskPOList = taskMapper.queryTaskList(taskQueryConditions);
        return taskPOList.stream().map(this::convert2Model).collect(Collectors.toList());
    }

    @Override
    public long countTask(TaskQueryConditions taskQueryConditions) {
        return taskMapper.countTask(taskQueryConditions);
    }

    @Override
    public List<Task> queryNotSuccessTaskSituation(LocalDateTime startTime, LocalDateTime endTime) {
        List<TaskPO> taskPOList = taskMapper.queryNotSuccessTaskSituation(startTime, endTime);
        return taskPOList.stream().map(this::convert2Model)
                .filter(task -> task.getTaskType() != null).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long deleteById(long taskId) {
        TaskPO taskPO = taskMapper.queryById(taskId);
        // 删除的任务可以放到备份表里
        return taskMapper.deleteById(taskId);
    }

    @Override
    public List<Task> queryTodayUnFinishedTask() {
        List<TaskPO> taskPOList = taskMapper.queryTodayUnFinishedTask(
                LocalDate.now().atStartOfDay());
        return taskPOList.stream().map(this::convert2Model).collect(Collectors.toList());
    }
}
