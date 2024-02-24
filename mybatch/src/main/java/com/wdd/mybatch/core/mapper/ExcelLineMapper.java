package com.wdd.mybatch.core.mapper;

import com.wdd.mybatch.core.po.ExcelLinePO;

public interface ExcelLineMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ExcelLinePO record);

    int insertSelective(ExcelLinePO record);

    ExcelLinePO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExcelLinePO record);

    int updateByPrimaryKeyWithBLOBs(ExcelLinePO record);

    int updateByPrimaryKey(ExcelLinePO record);
}