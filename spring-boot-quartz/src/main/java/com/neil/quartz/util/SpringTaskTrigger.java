package com.neil.quartz.util;

import com.neil.aware.ServiceLocator;
import com.neil.quartz.entity.TaskEntity;
import com.neil.quartz.task.Task;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

import java.text.ParseException;
import java.util.Date;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/2 11:25
 * @Version 1.0
 */
@Slf4j
public class SpringTaskTrigger implements TaskTrigger {
    private Scheduler scheduler = (Scheduler) ServiceLocator.lookup("schedulerManager");

    /**
     * 在 Quartz  配置类中，主要配置两个东西：1. JobDetail 2. Trigger
     * JobDetail 有两种不同的配置方式：
     * 1. MethodInvokingJobDetailFactoryBean
     * 2. JobDetailFactoryBean
     * @param taskEntity
     * @throws Exception
     */
    @Override
    public void cronTrigger(TaskEntity taskEntity) {
        try {
            //同个beanName的情况需要配多个job的，新建task实例bean，不能用同个bean
            Task task;
            task = (Task) ServiceLocator.lookup(taskEntity.getBeanName());
            task.setTaskEntity(taskEntity);
            task.initTask();

            // 新建一个基于Spring的管理Job类
            MethodInvokingJobDetailFactoryBean mjdfb = new MethodInvokingJobDetailFactoryBean();
            mjdfb.setName(taskEntity.getJobName()); // 设置Job名称
            mjdfb.setTargetObject(task);
            mjdfb.setTargetMethod("execute"); // 设置任务方法
            mjdfb.setConcurrent(true); // 设置是否并发启动任务
            mjdfb.afterPropertiesSet(); // 将管理Job类提交到计划管理类

            // 将Spring的管理Job类转为Quartz管理JobDetail类
            JobDetail jobDetail = mjdfb.getObject();
            // 新一个基于Spring的时间类
            CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
            triggerFactoryBean.setCronExpression(taskEntity.getCronExpr()); // 设置时间表达式
            triggerFactoryBean.setName(taskEntity.getJobName()); // 设置名称
            triggerFactoryBean.setJobDetail(jobDetail); // 注入Job
            triggerFactoryBean.afterPropertiesSet();

            scheduler.addJob(jobDetail, true); // 将Job添加到管理类
            scheduler.scheduleJob(triggerFactoryBean.getObject()); // 注入到管理类
            scheduler.rescheduleJob(TriggerKey.triggerKey(taskEntity.getJobName()), triggerFactoryBean.getObject()); // 刷新管理类
        }
        catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }
        catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }

        log.info(new Date() + ": A new task[" + taskEntity.getJobName() + "] was created.");
    }
}
