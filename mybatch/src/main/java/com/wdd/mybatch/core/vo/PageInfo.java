package com.wdd.mybatch.core.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageInfo <T>{

    private int pageNum;

    private int pageSize;

    private int total;

    private List<T> list;
}
