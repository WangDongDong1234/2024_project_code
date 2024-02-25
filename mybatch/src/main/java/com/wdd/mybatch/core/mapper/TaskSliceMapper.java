package com.wdd.mybatch.core.mapper;

import com.wdd.mybatch.core.po.TaskSlicePO;
import com.wdd.mybatch.core.vo.TaskSliceProgress;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface TaskSliceMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TaskSlicePO record);

    TaskSlicePO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskSlicePO record);

    int updateByPrimaryKey(TaskSlicePO record);

    /**
     * 根据taskId、状态查询
     *
     * @param taskId
     * @param taskSliceStatus
     * @param limit
     * @return
     */
    List<TaskSlicePO> queryByTaskIdStatus(@Param("taskId") long taskId,
                                          @Param("taskSliceStatus") String taskSliceStatus, @Param("limit") int limit);

    /**
     * 创建
     *
     * @param taskSlicePO
     */
    void insert(TaskSlicePO taskSlicePO);

    /**
     * 批量创建
     *
     * @param taskSlicePOs
     */
    void insertList(@Param("taskSlicePOs") List<TaskSlicePO> taskSlicePOs);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    TaskSlicePO queryById(long id);

    /**
     * 根据id更新
     *
     * @param taskSlicePO
     * @return
     */
    long updateById(TaskSlicePO taskSlicePO);

    /**
     * 根据taskId、状态统计
     *
     * @param taskId
     * @param taskSliceStatus
     * @return
     */
    long countByTaskIdStatus(@Param("taskId") long taskId,
                             @Param("taskSliceStatus") String taskSliceStatus);

    /**
     * 唯一索引查询
     *
     * @param taskId
     * @param businessId
     * @return
     */
    TaskSlicePO queryByTaskIdBizId(@Param("taskId") long taskId,
                                   @Param("businessId") String businessId);

    /**
     * 唯一索引批量查询
     *
     * @param taskId
     * @param businessIds
     * @return
     */
    Set<String> queryByTaskIdBizIds(@Param("taskId") long taskId,
                                    @Param("businessIds") List<String> businessIds);

    /**
     * 根据id加锁
     *
     * @param id
     * @return
     */
    TaskSlicePO lockById(long id);

    /**
     * 统计成功、失败数量
     *
     * @param taskId
     * @return
     */
    TaskSliceProgress sumSuccessFailCntByTaskId(long taskId);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    long deleteById(long id);

    /**
     * 根据taskId查询全部分片
     *
     * @param taskId
     * @return
     */
    List<TaskSlicePO> queryByTaskId(long taskId);

}