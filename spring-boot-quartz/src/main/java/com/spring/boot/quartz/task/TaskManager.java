package com.spring.boot.quartz.task;

import com.spring.boot.quartz.ServiceLocator;
import com.spring.boot.quartz.entity.TaskEntity;
import com.spring.boot.quartz.task.job.Print2Task;
import com.spring.boot.quartz.task.job.PrintTask;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/12/1 20:51
 * @Version 1.0
 */
@Slf4j
public final class TaskManager {
    private Scheduler scheduler = (Scheduler) ServiceLocator.lookup("schedulerManager");

    private volatile static TaskManager taskManager;

    private TaskManager() {
        if (taskManager != null) {
            throw new RuntimeException("单例不允许多个实例");
        }
    }

    public static TaskManager getInstance() {
        if (taskManager == null) {
            synchronized (TaskManager.class) {
                if (taskManager == null) {
                    taskManager = new TaskManager();
                }
            }
        }
        return taskManager;
    }

    /**
     * 在 Quartz  配置类中，主要配置两个东西：1. JobDetail 2. Trigger
     * JobDetail 有两种不同的配置方式：
     * 1. MethodInvokingJobDetailFactoryBean
     * 2. JobDetailFactoryBean
     * @param taskEntity
     * @throws Exception
     */
    public void createCronTrigger(TaskEntity taskEntity) throws Exception {
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
        //scheduler.shutdown();

        log.info(new Date() + ": A new task[" + taskEntity.getJobName() + "] was created.");
    }

    /**
     * 官方例子，创建表达式触发器
     * CronTriggerExample，RAM-based scheduler
     * @param taskEntity
     * @throws Exception
     */
    public void cronTrigger(TaskEntity taskEntity) throws Exception {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        JobDetail job = newJob(Print2Task.class)
                .withIdentity(taskEntity.getBeanName(), taskEntity.getJobName())
                .build();

        CronTrigger trigger = newTrigger()
                .withIdentity(taskEntity.getBeanName(), taskEntity.getJobName())
                .withSchedule(cronSchedule(taskEntity.getCronExpr()))
                .build();
        sched.scheduleJob(job, trigger);
        sched.start();
        //sched.shutdown();

        log.info(new Date() + ": A new task[" + taskEntity.getJobName() + "] was created.");
    }

    /**
     * 创建三个都是5秒触发的任务，配置优先级，第一次同时触发，第二次以交错的间隔触发
     * @throws Exception
     */
    public void priorityTrigger() throws Exception {
        JobDetail job = new JobDetailImpl("Print2Task", Print2Task.class);

        SchedulerFactory sf = new StdSchedulerFactory(
                "C:\\Tools\\IJetBrains\\IntelliJ IDEA 2018.3.5\\Projects\\neil-learning\\spring-boot-quartz\\src\\main\\resources\\quartz_priority.properties");
        Scheduler sched = sf.getScheduler();

        // Calculate the start time of all triggers as 5 seconds from now
        Date startTime = futureDate(5, IntervalUnit.SECOND);

        // First trigger has priority of 1, and will repeat after 5 seconds
        Trigger trigger1 = newTrigger()
                .withIdentity("PriorityNeg5Trigger5SecondRepeat")
                .startAt(startTime)
                .withSchedule(simpleSchedule().withRepeatCount(1).withIntervalInSeconds(5))
                .withPriority(1)
                .forJob(job)
                .build();

        // Second trigger has default priority of 5 (default), and will repeat after 10 seconds
        Trigger trigger2 = newTrigger()
                .withIdentity("Priority5Trigger10SecondRepeat")
                .startAt(startTime)
                .withSchedule(simpleSchedule().withRepeatCount(1).withIntervalInSeconds(10))
                .forJob(job)
                .build();

        // Third trigger has priority 10, and will repeat after 15 seconds
        Trigger trigger3 = newTrigger().withIdentity("Priority10Trigger15SecondRepeat")
                .startAt(startTime)
                .withSchedule(simpleSchedule().withRepeatCount(1).withIntervalInSeconds(15))
                .withPriority(10)
                .forJob(job)
                .build();

        // Tell quartz to schedule the job using our trigger
        sched.scheduleJob(job, trigger1);
        sched.scheduleJob(trigger2);
        sched.scheduleJob(trigger3);
        sched.start();

        //sched.shutdown();
    }
}
