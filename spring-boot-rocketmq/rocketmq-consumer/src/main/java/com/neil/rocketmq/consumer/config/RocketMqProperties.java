package com.neil.rocketmq.consumer.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Decription MQ相关配置
 * @Author NEIL
 * @Date 2022/11/28 15:15
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "sbr.rc.rocket.mq")
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
    private final Consumer consumer = new Consumer();

    @Setter
    @Getter
    public static class Namesrv {
        private String addr;
    }

    @Getter
    @Setter
    public static class Consumer {
        private final TestTopic testTopic = new TestTopic();

        @Getter
        @Setter
        public static class TestTopic {
            private String consumerGroup;
            private int consumerThreadMin;
            private int consumerThreadMax;
            private int consumerMessageBatchMaxSize;
            private ConsumeFromWhere consumerFromWhere;
            private String topic;
            private String tag;

        }
    }
}
