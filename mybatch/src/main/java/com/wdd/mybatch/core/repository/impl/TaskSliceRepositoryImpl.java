package com.wdd.mybatch.core.repository.impl;

import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.entity.TaskSlice;
import com.wdd.mybatch.core.enums.TaskSliceStatusEnum;
import com.wdd.mybatch.core.mapper.TaskSliceMapper;
import com.wdd.mybatch.core.po.TaskSlicePO;
import com.wdd.mybatch.core.repository.TaskRepository;
import com.wdd.mybatch.core.repository.TaskSliceRepository;
import com.wdd.mybatch.core.vo.TaskSliceProgress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TaskSliceRepositoryImpl implements TaskSliceRepository {

    @Autowired
    private TaskSliceMapper taskSliceMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void insert(TaskSlice taskSlice) {
        TaskSlicePO taskSlicePO = convert2PO(taskSlice);
        taskSliceMapper.insert(taskSlicePO);
    }

    @Override
    public void insertList(List<TaskSlice> taskSliceList) {
        if (CollectionUtils.isEmpty(taskSliceList)) {
            return;
        }

        List<TaskSlicePO> taskSlicePOs = taskSliceList.stream().map(e -> convert2PO(e)).collect(Collectors.toList());
        taskSliceMapper.insertList(taskSlicePOs);
    }


    @Override
    public List<TaskSlice> queryByTaskIdStatus(Task task, TaskSliceStatusEnum taskSliceStatus,
                                               int cnt) {
        List<TaskSlicePO> taskSlicePOList = taskSliceMapper.queryByTaskIdStatus(task.getId(), taskSliceStatus.name(),
                        cnt);

        return taskSlicePOList.stream().map(item -> convert2Model(item, task))
                .collect(Collectors.toList());
    }

    @Override
    public TaskSliceProgress sumSuccessFailCntByTaskId(long taskId) {
        return taskSliceMapper.sumSuccessFailCntByTaskId(taskId);
    }

    @Override
    public long countByTaskIdStatus(long taskId, TaskSliceStatusEnum taskSliceStatus) {
        return taskSliceMapper.countByTaskIdStatus(taskId, taskSliceStatus.name());
    }

    @Override
    public long updateById(TaskSlice taskSlice) {
        TaskSlicePO taskSlicePO = convert2PO(taskSlice);
        return taskSliceMapper.updateById(taskSlicePO);
    }

    @Override
    public TaskSlice queryById(long taskSliceId) {
        TaskSlicePO taskSlicePO = taskSliceMapper.queryById(taskSliceId);
        return buildTaskSlice(taskSlicePO);
    }

    /**
     * 组装task
     *
     * @param taskSlicePO
     * @return
     */
    private TaskSlice buildTaskSlice(TaskSlicePO taskSlicePO) {
        if (taskSlicePO == null) {
            return null;
        }

        Task task = taskRepository.queryById(taskSlicePO.getTaskId());
        return convert2Model(taskSlicePO, task);
    }

    @Override
    public TaskSlice lockById(long taskSliceId) {
        TaskSlicePO taskSlicePO = taskSliceMapper.lockById(taskSliceId);
        return buildTaskSlice(taskSlicePO);
    }


    @Override
    public TaskSlice queryByTaskIdBizId(long taskId, String businessId) {
        TaskSlicePO taskSlicePO = taskSliceMapper.queryByTaskIdBizId(taskId, businessId);
        return buildTaskSlice(taskSlicePO);
    }

    @Override
    public Set<String> queryByTaskIdBizIds(long taskId, List<String> businessIds) {
        return  taskSliceMapper.queryByTaskIdBizIds(taskId, businessIds);
    }

    @Override
    public long deleteById(long taskSliceId) {
        return taskSliceMapper.deleteById(taskSliceId);
    }

    @Override
    public List<TaskSlice> queryByTaskId(long taskId) {
        Task task = taskRepository.queryById(taskId);
        List<TaskSlicePO> taskSlicePOList = taskSliceMapper.queryByTaskId(taskId);
        return taskSlicePOList.stream().map(item -> convert2Model(item, task))
                .collect(Collectors.toList());
    }

    @Override
    public TaskSliceProgress sumSliceProgress(long taskId) {
        return taskSliceMapper.sumSuccessFailCntByTaskId(taskId);
    }

    private TaskSlice convert2Model(TaskSlicePO taskSlicePO, Task task) {
        if (taskSlicePO == null) {
            return null;
        }

        TaskSlice taskSlice = new TaskSlice();
        taskSlice.setId(taskSlicePO.getId());
        taskSlice.setTask(task);
        taskSlice.setBusinessId(taskSlicePO.getBusinessId());
        taskSlice.setSliceStatus(TaskSliceStatusEnum.valueOf(taskSlicePO.getSliceStatus()));
        taskSlice.setSliceData(taskSlicePO.getSliceData());
        taskSlice.setFailReason(taskSlicePO.getFailReason());
        taskSlice.setStartTime(taskSlicePO.getStartTime());
        taskSlice.setEndTime(taskSlicePO.getEndTime());
        taskSlice.setTotalCnt(taskSlicePO.getTotalCnt());
        taskSlice.setSuccessCnt(taskSlicePO.getSuccessCnt());
        taskSlice.setFailCnt(taskSlicePO.getFailCnt());
        taskSlice.setSkipCnt(taskSlicePO.getSkipCnt());
        taskSlice.setRoundNumber(taskSlicePO.getRoundNumber());
        taskSlice.setUpdateTime(taskSlicePO.getUpdateTime());
        return taskSlice;
    }

    private TaskSlicePO convert2PO(TaskSlice taskSlice) {
        if (taskSlice == null) {
            return null;
        }

        TaskSlicePO taskSlicePO = new TaskSlicePO();
        taskSlicePO.setId(taskSlice.getId());
        taskSlicePO.setTaskId(taskSlice.getTask().getId());
        taskSlicePO.setBusinessId(taskSlice.getBusinessId());
        taskSlicePO.setSliceStatus(taskSlice.getSliceStatus().name());
        taskSlicePO.setFailReason(taskSlice.getFailReason());
        taskSlicePO.setSliceData(taskSlice.getSliceData());
        taskSlicePO.setStartTime(taskSlice.getStartTime());
        taskSlicePO.setEndTime(taskSlice.getEndTime());
        taskSlicePO.setTotalCnt(taskSlice.getTotalCnt());
        taskSlicePO.setSuccessCnt(taskSlice.getSuccessCnt());
        taskSlicePO.setFailCnt(taskSlice.getFailCnt());
        taskSlicePO.setSkipCnt(taskSlice.getSkipCnt());
        taskSlicePO.setRoundNumber(taskSlice.getRoundNumber());
        return taskSlicePO;
    }
}
