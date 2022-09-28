package com.neil.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/9/20 15:07
 * @Version 1.0
 */
@RestController
public class TestController {
    @RequestMapping
    public String get() {
        return "test";
    }
}
