package com.neil.quartz.task.job;

import com.neil.quartz.task.Task;
import com.neil.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/9/14 11:05
 * @Version 1.0
 */
@Slf4j
@Component
public class PrintTask2 extends Task {
    @Override
    public void initTask() {
        // 初始化
        log.info("PrintTask2 init...");
    }

    /**
     * 本身就是异步执行，不需要关注任务执行时长
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Print2###################################, name：PrintTask2");
        //Tools.sleep(20 * 1000L);
    }
}
