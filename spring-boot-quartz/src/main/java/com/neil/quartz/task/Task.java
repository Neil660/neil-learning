package com.neil.quartz.task;

import com.neil.quartz.entity.TaskEntity;
import org.quartz.Job;

/**
 * @Decription 任务抽象类
 * @Author NEIL
 * @Date 2022/12/1 20:48
 * @Version 1.0
 */
public abstract class Task implements Job {
    protected TaskEntity taskEntity;

    public void setTaskEntity(TaskEntity taskEntity) {
        this.taskEntity = taskEntity;
    }

    public TaskEntity getTaskEntity() {
        return taskEntity;
    }

    public abstract void initTask();
}
