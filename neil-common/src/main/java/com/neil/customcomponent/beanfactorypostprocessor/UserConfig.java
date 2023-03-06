package com.neil.customcomponent.beanfactorypostprocessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/4 21:49
 * @Version 1.0
 */
@Configuration
public class UserConfig {

    @Bean(name = "user")
    public User user() {
        User wuQian = new User("WuQian", 18);
        System.out.println("Configuration.user,user=" + wuQian.toString());
        return wuQian;
    }
}
