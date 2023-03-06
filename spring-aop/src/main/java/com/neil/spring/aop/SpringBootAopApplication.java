package com.neil.spring.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/11/18 11:00
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.neil")
public class SpringBootAopApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootAopApplication.class, args);
        // 查看使用的代理模式
        /*UserService userService = context.getBean(UserService.class);
        userService.work();*/
    }
}
