package com.spring.boot.quartz;

import com.spring.boot.quartz.entity.TaskEntity;
import com.spring.boot.quartz.task.TaskManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootQuartzApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootQuartzApplication.class, args);

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setBeanName("printTask");
        taskEntity.setCronExpr("0/5 * * * * ?");
        taskEntity.setJobName("Print Job");

        TaskManager manager = TaskManager.getInstance();
        //manager.createCronTrigger(taskEntity);
        //manager.CronTriggerExample(taskEntity);
        manager.priorityTrigger();
    }

}
