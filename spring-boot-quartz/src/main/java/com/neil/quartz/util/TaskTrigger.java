package com.neil.quartz.util;

import com.neil.quartz.entity.TaskEntity;

/**
 * @Decription 触发器
 * @Author NEIL
 * @Date 2023/3/2 11:23
 * @Version 1.0
 */
public interface TaskTrigger {
    void cronTrigger(TaskEntity taskEntity);
}
