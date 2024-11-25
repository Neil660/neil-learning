package com.neil.quartz;

import com.neil.quartz.util.DefaultTaskTrigger;
import com.neil.quartz.task.TaskManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/2 10:51
 * @Version 1.0
 */
@Component
public class QuartzServer implements CommandLineRunner {

    @Override
    public void run(String... args0) {
        TaskManager instance = TaskManager.getInstance();
        instance.setTaskTrigger(new DefaultTaskTrigger());
        instance.init();
    }
}
