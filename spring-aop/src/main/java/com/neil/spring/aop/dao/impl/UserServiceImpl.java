package com.neil.spring.aop.dao.impl;

import com.neil.spring.aop.dao.UserService;
import org.springframework.stereotype.Service;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/2/14 10:34
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void work() {
        System.out.println("UserService is working...");
    }
}
