package com.wdd.mybatch.core.mapper;

import com.wdd.mybatch.core.po.TaskPO;

public interface TaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TaskPO record);

    int insertSelective(TaskPO record);

    TaskPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskPO record);

    int updateByPrimaryKeyWithBLOBs(TaskPO record);

    int updateByPrimaryKey(TaskPO record);
}