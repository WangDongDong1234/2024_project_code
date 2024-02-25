package com.wdd.mybatch.core.repository;

import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.entity.TaskSlice;
import com.wdd.mybatch.core.enums.TaskSliceStatusEnum;
import com.wdd.mybatch.core.vo.TaskSliceProgress;

import java.util.List;
import java.util.Set;

public interface TaskSliceRepository {
    /**
     * 创建分片
     *
     * @param taskSlice
     */
    void insert(TaskSlice taskSlice);

    /**
     * 批量创建分片
     *
     * @param taskSliceList
     */
    void insertList(List<TaskSlice> taskSliceList);

    /**
     * 查询分片
     *
     * @param task
     * @param taskSliceStatus
     * @param cnt
     * @return
     */
    List<TaskSlice> queryByTaskIdStatus(Task task, TaskSliceStatusEnum taskSliceStatus, int cnt);

    /**
     * 统计成功、失败行数
     *
     * @param taskId
     * @return
     */
    TaskSliceProgress sumSuccessFailCntByTaskId(long taskId);

    /**
     * 根据taskId统计
     *
     * @param taskId
     * @param success
     * @return
     */
    long countByTaskIdStatus(long taskId, TaskSliceStatusEnum success);

    /**
     * 根据id更新
     *
     * @param taskSlice
     */
    long updateById(TaskSlice taskSlice);

    /**
     * 根据id查询
     *
     * @param taskSliceId
     * @return
     */
    TaskSlice queryById(long taskSliceId);

    /**
     * 根据taskId、businessId查询
     *
     * @param taskId
     * @param businessId
     * @return
     */
    TaskSlice queryByTaskIdBizId(long taskId, String businessId);

    /**
     * 根据taskId、businessIds 批量查询
     *
     * @param taskId
     * @param businessIds
     * @return
     */
    Set<String> queryByTaskIdBizIds(long taskId, List<String> businessIds);

    /**
     * 根据id加锁
     *
     * @param taskSliceId
     * @return
     */
    TaskSlice lockById(long taskSliceId);

    /**
     * 根据id删除
     *
     * @param taskSliceId
     * @return
     */
    long deleteById(long taskSliceId);

    /**
     * 根据taskId查询全部分片
     *
     * @param taskId
     * @return
     */
    List<TaskSlice> queryByTaskId(long taskId);

    /**
     * 根据taskId汇总进度
     *
     * @param taskId
     * @return
     */
    TaskSliceProgress sumSliceProgress(long taskId);
}
