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

    @Value("${spring.boot.quartz.run-args:timer}")
    private String runArgs;

    @Override
    public void run(String... args0) throws Exception {
        if (args0.length < 1) {
            args0 = new String[1];
            args0[0] = runArgs;
        }
        String[] args = args0[0].split(",");
        for (int i = 0; i < args.length; i++) {
            // 启动定时任务
            if ("timer".equalsIgnoreCase(args[i])) {
                TaskManager instance = TaskManager.getInstance();
                instance.setTaskTrigger(new DefaultTaskTrigger());
                instance.init();
            }
        }
    }
}
