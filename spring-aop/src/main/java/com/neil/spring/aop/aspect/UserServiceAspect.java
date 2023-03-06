package com.neil.spring.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/2/14 10:35
 * @Version 1.0
 */
@Component
@Aspect
public class UserServiceAspect {
    @Before("userServiceAspectPointcut()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("aspect before");
    }

    @AfterReturning(returning = "obj", pointcut = "userServiceAspectPointcut()")
    public void processControllerAfter(JoinPoint joinPoint, Object obj) {
        Object[] args = joinPoint.getArgs();
        System.out.println("执行结果是：" + obj.toString());
    }

    @Pointcut("execution(* com.neil.spring.aop.dao.UserService.work(..))")
    public void userServiceAspectPointcut() {
    }
}
