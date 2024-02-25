package com.wdd.mybatch.core.mapper;

import com.wdd.mybatch.core.enums.TaskTypeEnum;
import com.wdd.mybatch.core.po.TaskPO;
import com.wdd.mybatch.core.vo.TaskQueryConditions;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskMapper {
    int deleteByPrimaryKey(Long id);

    //int insert(TaskPO record);

    int insertSelective(TaskPO record);

    TaskPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskPO record);

    int updateByPrimaryKeyWithBLOBs(TaskPO record);

    int updateByPrimaryKey(TaskPO record);

    /**
     * 唯一索引查询
     *
     * @param businessTime
     * @param taskType
     * @param businessId
     * @return
     */
    TaskPO queryByTimeTypeBizId(@Param("businessTime") LocalDateTime businessTime,
                                @Param("taskType") String taskType, @Param("businessId") String businessId);

    /**
     * 创建一个任务
     *
     * @param taskPO
     */
    void insert(TaskPO taskPO);

    /**
     * 根据type、status查询
     *
     * @param taskType
     * @param taskStatusList
     * @param limit
     * @param businessTimeStart
     * @param businessTimeEnd
     * @return
     */
    List<TaskPO> queryByTypeStatus(@Param("taskType") String taskType,
                                   @Param("taskStatusList") List<String> taskStatusList, @Param("limit") int limit,
                                   @Param("businessTimeStart") LocalDateTime businessTimeStart,
                                   @Param("businessTimeEnd") LocalDateTime businessTimeEnd);

    /**
     * 根据id更新
     *
     * @param taskPO
     * @return
     */
    long updateById(TaskPO taskPO);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    TaskPO queryById(long id);

    /**
     * 根据时间+类型查询
     *
     * @param businessTime
     * @param taskType
     * @param fuzzyBusinessId
     * @return
     */
    List<TaskPO> queryByTimeTypeFuzzyBizId(@Param("businessTime") LocalDateTime businessTime,
                                           @Param("taskType") TaskTypeEnum taskType,
                                           @Param("fuzzyBusinessId") String fuzzyBusinessId);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    long deleteById(long id);

    /**
     * 根据id加锁
     *
     * @param id
     * @return
     */
    TaskPO lockById(long id);

    /**
     * 查询当日长时间未完成的任务
     *
     * @param minBusinessTime
     * @return
     */
    List<TaskPO> queryTodayUnFinishedTask(LocalDateTime minBusinessTime);

    /**
     * 查询任务列表
     *
     * @param taskType
     * @param businessId
     * @param businessTimeStart
     * @param businessTimeEnd
     * @return
     */
    List<TaskPO> queryByTypeBizIdTime(TaskTypeEnum taskType, String businessId,
                                      LocalDateTime businessTimeStart, LocalDateTime businessTimeEnd);

    /**
     * 按照操作人和业务时间查询
     *
     * @param operator
     * @param businessTimeStart
     * @param businessTimeEnd
     * @param start
     * @param limit
     * @return
     */
    List<TaskPO> queryTaskByOperatorAndBizTime(String operator,
                                               LocalDateTime businessTimeStart, LocalDateTime businessTimeEnd,
                                               @Param("start") int start, @Param("limit") int limit);

    /**
     * 查询未成功的任务
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<TaskPO> queryNotSuccessTaskSituation(@Param("startTime")LocalDateTime startTime,
                                              @Param("endTime") LocalDateTime endTime);

    /**
     * 计数
     *
     * @param taskQueryConditions
     * @return
     */
    long countTask(TaskQueryConditions taskQueryConditions);

    /**
     * 分页搜索
     *
     * @param taskQueryConditions
     * @return
     */
    List<TaskPO> queryTaskList(TaskQueryConditions taskQueryConditions);

}