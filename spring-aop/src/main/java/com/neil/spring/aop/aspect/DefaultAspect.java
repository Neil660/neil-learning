package com.neil.spring.aop.aspect;

import com.neil.spring.aop.model.SysLog;
import com.neil.spring.aop.annotation.Log;
import com.neil.spring.aop.dao.SysLogDao;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Random;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/11/17 17:05
 * @Version 1.0
 */
@Slf4j
@Aspect
@Component
public class DefaultAspect {
    @Autowired
    SysLogDao sysLogDao;

    /**
     * 切入点
     * public表示只匹配该类下的public方法，后面的*代表返回值类型任意
     * ..代表com.neil.spring.aop.controller包以及子包，后面的*代表任意类，add*(..)代表以add开头的任意方法
     */
    @Pointcut("execution(public * com.neil.spring.aop.controller..*.add*(..)) || " +
            "execution(public * com.neil.spring.aop.controller..*.del*(..)) || " +
            "execution(public * com.neil.spring.aop.controller..*.update*(..)) || " +
            "execution(public * com.neil.spring.aop.controller..*.get*(..)) ")
    public void controllerPointcut() {
    }

    /**
     * 前置通知
     */
    @Before("controllerPointcut()")
    public void processControllerBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info("执行方法前的入参是：" + args.toString());
    }

    /**
     * 后置返回通知
     *
     * @AfterThrowing:后置异常通知
     * @After:后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     * @Around:环绕通知,环绕增强，相当于MethodInterceptor
     */
    @AfterReturning(returning = "obj", pointcut = "controllerPointcut()")
    public void processControllerAfter(JoinPoint joinPoint, Object obj) {
        Object[] args = joinPoint.getArgs();
        log.info("执行结果是：" + obj.toString());
    }

    /**
     * 后置异常通知
     * @param joint
     */
    @AfterThrowing(value = "controllerPointcut()")
    public void afterThrowException(JoinPoint joint) {
        log.error("执行异常");
    }

    /**
     * 环绕通知
     * @param point
     */
    @Around("controllerPointcut()")
    public void around(ProceedingJoinPoint point) {
        try {
            // 执行方法
            point.proceed();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        saveLog(point);
    }

    private void saveLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();
        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null) {
            // 注解上的描述
            sysLog.setOperation(logAnnotation.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params += "  " + paramNames[i] + ": " + args[i];
            }
            sysLog.setParams(params);
        }
        // 获取request
        // 设置IP地址
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        ip = "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
        sysLog.setIp(ip);
        // 模拟一个用户名
        sysLog.setId(new Random().nextInt());
        sysLog.setUsername("mrbird");
        sysLog.setCreateTime(new Date());
        // 保存系统日志
        sysLogDao.addSysLog(sysLog);
    }

}
