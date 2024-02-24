package com.wdd.mybatch.core.mapper;

import com.wdd.mybatch.core.po.TaskSlicePO;

public interface TaskSliceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TaskSlicePO record);

    int insertSelective(TaskSlicePO record);

    TaskSlicePO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskSlicePO record);

    int updateByPrimaryKey(TaskSlicePO record);
}