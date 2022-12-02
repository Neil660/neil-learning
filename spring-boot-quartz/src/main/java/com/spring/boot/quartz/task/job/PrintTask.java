package com.spring.boot.quartz.task.job;

import com.spring.boot.quartz.task.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Decription
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
    }

    @Override
    public void execute() throws Exception {
        log.info("Print===============================, name：{}", this.getTaskEntity().getBeanName());
    }
}
