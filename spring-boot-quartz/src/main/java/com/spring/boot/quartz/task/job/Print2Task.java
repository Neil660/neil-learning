package com.spring.boot.quartz.task.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/12/2 15:08
 * @Version 1.0
 */
@Slf4j
public class Print2Task implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Print===============================, name：{}", context.getTrigger().getKey());
    }
}
