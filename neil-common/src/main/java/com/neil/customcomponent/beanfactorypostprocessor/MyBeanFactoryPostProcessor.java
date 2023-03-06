package com.neil.customcomponent.beanfactorypostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @Decription 将User的name改为其他值
 * 实现BeanFactoryPostProcessor接口， 修改Bean信息
 * 使用场景：（1）对敏感信息的解密处理； （2）占位符处理
 * @Author NEIL
 * @Date 2023/3/4 21:49
 * @Version 1.0
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition bd = beanFactory.getBeanDefinition("user");
        //开始修改属性的值
        bd.getPropertyValues().add("userName", "WuQian-modification");
    }
}
