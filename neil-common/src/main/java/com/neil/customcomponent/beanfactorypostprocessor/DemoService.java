package com.neil.customcomponent.beanfactorypostprocessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/4 21:51
 * @Version 1.0
 */
@Service
public class DemoService {

    @Autowired
    private User user;

    // @PostConstruct：依赖注入完成后，会执行该方法
    @PostConstruct
    public void init() {
        System.out.println("new user=" + user.toString());
    }
}
