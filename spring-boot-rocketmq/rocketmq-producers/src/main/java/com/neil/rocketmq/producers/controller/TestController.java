package com.neil.rocketmq.producers.controller;

import com.neil.rocketmq.producers.producer.RocketMqProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
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
@Slf4j
public class TestController {
    @Autowired
    RocketMqProducer producers;

    @GetMapping("/send/{msg}")
    public String send(@PathVariable String msg) {
        SendResult sendResult = null;
        try {
            sendResult = (SendResult) producers.send("test_topic", "*", msg);
        }
        catch (Exception e) {
            log.error("", e.getMessage(), e);
        }
        return sendResult.toString();
    }
}
