package com.spring.boot.rocketmq.producers.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Decription MQ相关配置
 * @Author NEIL
 * @Date 2022/11/28 15:15
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "sbr.rp.rocket.mq")
@Setter
@Getter
public class RocketMqProperties {
    private int consume_thread_max;
    private int consume_thread_min;
    private String defaultTag;
    private String namesrvAddr;
    private String namespace;
    private String producerGroup;
    private long producer_delay_time_mills_when_send_ex;
    private int producer_max_resend_time_when_send_ex;

    private final Namesrv namesrv = new Namesrv();
    private final Producer producer = new Producer();
    private final Topic topic = new Topic();

    @Setter
    @Getter
    public static class Namesrv {
        private String addr;
    }

    @Setter
    @Getter
    public static class Topic {
        private String testTopic;
    }

    @Setter
    @Getter
    public static class Producer {
        private String producerGroup;
        private long delayTimeMillsWhenSendEx;
        private int maxReSendTimeWhenSendEx;
    }
}
