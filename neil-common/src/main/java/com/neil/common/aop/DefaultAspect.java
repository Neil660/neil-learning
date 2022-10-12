package com.neil.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/10/12 15:59
 * @Version 1.0
 */
@Slf4j
@Aspect
@Component
public class DefaultAspect {

    /**
     * 切入点
     * public表示只匹配该类下的public方法，后面的*代表返回值类型任意
     * ..代表com.neil.common.controller包以及子包，后面的*代表任意类，add*(..)代表以add开头的任意方法
     */
    @Pointcut("execution(public * com.neil.common.controller..*.add*(..)) || " +
            "execution(public * com.neil.common.controller..*.del*(..)) || " +
            "execution(public * com.neil.common.controller..*.update*(..)) || " +
            "execution(public * com.neil.common.controller..*.get*(..)) ")
    public void controllerPointcut() {}

    /**
     * 前置通知
     */
    @Before("controllerPointcut()")
    public void processControllerBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info("process Controller Before===================");
    }

    /**
     * 后置返回通知
     * @AfterThrowing:后置异常通知
     * @After:后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     * @Around:环绕通知,环绕增强，相当于MethodInterceptor
     *
     */
    @AfterReturning(returning = "obj", pointcut = "controllerPointcut()")
    public void processControllerAfter(JoinPoint joinPoint, Object obj) {
        Object[] args = joinPoint.getArgs();
        log.info("process Controller After===================");
    }

}
