package com.neil.kafka.producers.producers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/11/22 20:19
 * @Version 1.0
 */
@Slf4j
@Component
public class TestProducers {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 需要先创建一个名称为test_topic的topic
     * @param msg
     */
    public void send(String msg) {
        this.kafkaTemplate.send("test_topic", msg);
    }

    public void sendCallback(String msg) {
        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send("test_topic", msg);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Sending msg error,topic is test_topic", ex);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Sending msg to 'test_topic' successful");
            }
        });
    }
}
