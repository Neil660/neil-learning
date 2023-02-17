package com.spring.boot.aop;

import com.spring.boot.aop.dao.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/11/18 11:00
 * @Version 1.0
 */
@SpringBootApplication
public class SpringBootAopApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootAopApplication.class, args);
        // 查看使用的代理模式
        UserService userService = context.getBean(UserService.class);
        userService.work();
    }
}
