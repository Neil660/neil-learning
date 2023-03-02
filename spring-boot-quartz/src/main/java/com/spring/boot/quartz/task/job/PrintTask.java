package com.spring.boot.quartz.task.job;

import com.spring.boot.quartz.task.Task;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * @Decription 具体的任务，整体框架已经写好，后续只要向数据库插入任务的信息跟写一个Bean继承Task即可
 * @Author NEIL
 * @Date 2022/12/1 22:41
 * @Version 1.0
 */
@Slf4j
@Component
public class PrintTask extends Task {
    @Override
    public void initTask() {
        // 初始化
        log.info("PrintTask init...");
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 5秒打印一次的任务
        log.info("Print===============================, name：PrintTask");
    }
}
