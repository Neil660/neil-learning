package com.neil.quartz.task.job;

import com.neil.quartz.task.Task;
import com.neil.utils.Tools;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 本身就是异步执行，不需要关注任务执行时长
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 3秒打印一次的任务
        log.info("Print===============================, name：PrintTask");
        Tools.sleep(5 * 1000L);
    }
}
