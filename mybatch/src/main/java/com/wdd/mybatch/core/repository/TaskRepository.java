package com.wdd.mybatch.core.repository;

import com.wdd.mybatch.core.entity.Task;

public interface TaskRepository {

    /**
     * 根据id更新
     * @param task
     * @return
     */
    long updateById(Task task);
}
