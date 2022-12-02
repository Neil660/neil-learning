package com.spring.boot.quartz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/12/1 22:36
 * @Version 1.0
 */
@Configuration
public class AutoConfiguration {

    @Bean("schedulerManager")
    public SchedulerFactoryBean getSchedulerManager() {
        return new SchedulerFactoryBean();
    }
}
