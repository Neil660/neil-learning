package com.spring.boot.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/12/1 21:59
 * @Version 1.0
 */
@Slf4j
@Component
public class ServiceLocator implements ApplicationContextAware {
    private static ApplicationContext ctx = null;//new ClassPathXmlApplicationContext("classpath:/config/spring/spring.xml");;

    private ServiceLocator() {
    }

    public static Object lookup(String name) {
        Object obj = ctx.getBean(name);
        if (obj == null) {
            log.error("cannot find bean:" + name);
        }
        return obj;
    }

    public static Object lookup(Class<?> clazz) {
        Object obj = ctx.getBean(clazz);
        if (obj == null) {
            log.error("cannot find bean class type: :" + clazz.getName());
        }
        return obj;
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        ctx = arg0;
    }
}
