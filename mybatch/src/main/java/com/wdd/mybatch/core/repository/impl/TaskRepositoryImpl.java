package com.wdd.mybatch.core.repository.impl;

import com.wdd.mybatch.core.entity.Task;
import com.wdd.mybatch.core.repository.TaskRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    @Override
    public long updateById(Task task) {
        return 0;
    }
}
