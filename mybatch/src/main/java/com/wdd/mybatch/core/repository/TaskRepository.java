package com.wdd.mybatch.core.repository;

import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.enums.TaskStatusEnum;
import com.wdd.mybatch.core.enums.TaskTypeEnum;
import com.wdd.mybatch.core.vo.PageInfo;
import com.wdd.mybatch.core.vo.TaskQueryConditions;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository {

    /**
     * 唯一索引查询
     *
     * @param businessTime
     * @param taskType
     * @param businessId
     * @return
     */
    Task queryByTimeTypeBizId(LocalDateTime businessTime, TaskTypeEnum taskType, String businessId);

    /**
     * 创建任务
     *
     * @param task
     */
    void insert(Task task);

    /**
     * 根据状态查询
     *
     * @param taskType
     * @param dispatchStatus
     * @param cnt
     * @param businessTimeStart
     * @param businessTimeEnd
     * @return
     */
    List<Task> queryByTypeStatus(TaskTypeEnum taskType, TaskStatusEnum[] dispatchStatus,
                                 int cnt, LocalDateTime businessTimeStart, LocalDateTime businessTimeEnd);

    /**
     * 根据id更新
     *
     * @param task
     * @return
     */
    long updateById(Task task);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Task queryById(long id);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Task lockById(long id);

    /**
     * 根据时间+类型查询列表
     *
     * @param businessTime
     * @param taskType
     * @param productCode
     * @return
     */
    List<Task> queryByTimeTypeFuzzyBizId(LocalDateTime businessTime, TaskTypeEnum taskType,
                                         String productCode);

    /**
     * 根据id删除
     *
     * @param taskId
     * @return
     */
    long deleteById(long taskId);

    /**
     * 当日长时间未完成任务列表
     *
     * @return
     */
    List<Task> queryTodayUnFinishedTask();

    /**
     * 查询任务列表
     *
     * @param taskType
     * @param businessId
     * @param businessTimeStart
     * @param businessTimeEnd
     * @return
     */
    List<Task> queryByTypeBizIdTime(TaskTypeEnum taskType, String businessId,
                                    LocalDateTime businessTimeStart, LocalDateTime businessTimeEnd);

    /**
     * 查询
     *
     * @param operator
     * @param businessTimeStart
     * @param businessTimeEnd
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<Task> queryTaskByOperatorAndBizTime(String operator,
                                                 LocalDateTime businessTimeStart, LocalDateTime businessTimeEnd, int pageNo,
                                                 int pageSize);

    /**
     * 查询未成功的任务
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<Task> queryNotSuccessTaskSituation(LocalDateTime startTime,LocalDateTime endTime);

    /**
     * 查询任务列表
     *
     * @param taskQueryConditions
     * @return
     */
    List<Task> queryTaskList(TaskQueryConditions taskQueryConditions);

    /**
     * 统计任务数
     *
     * @param taskQueryConditions
     * @return
     */
    long countTask(TaskQueryConditions taskQueryConditions);

}
