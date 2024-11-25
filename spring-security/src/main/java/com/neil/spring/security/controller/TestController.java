package com.neil.spring.security.controller;

import com.neil.spring.security.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/2/20 10:46
 * @Version 1.0
 */
@RestController
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("/hello")
    public String request() {
        return "hello";
    }
}
