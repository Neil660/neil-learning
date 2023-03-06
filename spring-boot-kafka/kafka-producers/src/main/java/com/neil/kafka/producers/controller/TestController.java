package com.neil.kafka.producers.controller;

import com.neil.kafka.producers.producers.TestProducers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/11/22 20:20
 * @Version 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    TestProducers producers;

    @GetMapping("/send/{msg}")
    public void send(@PathVariable String msg) {
        producers.send(msg);
    }
}
