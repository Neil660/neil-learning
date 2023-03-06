package com.neil.quartz;

import com.neil.quartz.entity.TaskEntity;
import com.neil.quartz.task.TaskManager;
import com.neil.quartz.task.job.PrintTask;
import com.neil.quartz.util.DefaultTaskTrigger;
import org.junit.jupiter.api.Test;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@SpringBootTest
class SpringBootQuartzApplicationTests {

    @Test
    void contextLoads() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setBeanName("printTask");
        taskEntity.setCronExpr("0/5 * * * * ?");
        taskEntity.setJobName("Print-Job");

        TaskManager manager = TaskManager.getInstance();
        manager.setTaskTrigger(new DefaultTaskTrigger());
        manager.getTaskTrigger().cronTrigger(taskEntity);
        System.out.println();

        //priorityTrigger();
    }

    /**
     * 创建三个都是5秒触发的任务，配置优先级，第一次同时触发，第二次以交错的间隔触发
     * @throws Exception
     */
    public void priorityTrigger() throws Exception {
        JobDetail job = newJob(PrintTask.class).withIdentity("job1", "group1").build();

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
