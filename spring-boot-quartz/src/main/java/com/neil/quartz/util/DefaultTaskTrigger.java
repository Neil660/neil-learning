package com.neil.quartz.util;

import com.neil.aware.ServiceLocator;
import com.neil.quartz.entity.TaskEntity;
import com.neil.quartz.task.Task;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @Decription 官方推荐的方式
 * @Author NEIL
 * @Date 2023/3/2 11:23
 * @Version 1.0
 */
@Slf4j
public class DefaultTaskTrigger implements TaskTrigger {
    @Override
    public void cronTrigger(TaskEntity taskEntity) {
        String beanName = taskEntity.getBeanName();
        String jobName = taskEntity.getJobName();
        Task bean = (Task) ServiceLocator.lookup(beanName);
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = null;

        try {
            sched = sf.getScheduler();
            bean.initTask();
            JobDetail job = newJob(bean.getClass())
                    .withIdentity(beanName, jobName)
                    .build();

            CronTrigger trigger = newTrigger()
                    .withIdentity(beanName, jobName)
                    .withSchedule(cronSchedule(taskEntity.getCronExpr()))
                    .build();
            sched.scheduleJob(job, trigger);
            sched.start();
        }
        catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            try {
                sched.shutdown();
            }
            catch (SchedulerException e1) {

            }
            return ;
        }
        log.info(new Date() + ": A new task[" + jobName + "] was created.");
    }
}
