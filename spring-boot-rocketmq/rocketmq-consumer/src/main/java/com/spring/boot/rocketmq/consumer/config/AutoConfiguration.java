package com.spring.boot.rocketmq.consumer.config;

import com.spring.boot.rocketmq.consumer.consumer.TestTopicConsumerListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.HashMap;
import java.util.Map;


/**
 * @Decription 自动配置类
 * @Author NEIL
 * @Date 2022/11/28 15:14
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties({RocketMqProperties.class})
@EnableAsync
@Slf4j
public class AutoConfiguration {

    /**
     * test_topic主题的监听器
     * @return
     */
    @Bean("testTopicListener")
    @ConditionalOnClass(RocketMQConditional.class)
    public MessageListener getMessageListener() {
        return new TestTopicConsumerListener();
    }

    /**
     * test_topic主题的消费者
     * @param rocketMqProperties
     * @param testTopicListener
     * @return
     */
    @Conditional(RocketMQConditional.class)
    @Bean(name = "testTopicConsumer", initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQPushConsumer getDefaultMQPushConsumer(RocketMqProperties rocketMqProperties, MessageListener testTopicListener) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        consumer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        // 注册回调接口来处理从Broker中收到的消息
        consumer.setMessageListener(testTopicListener);
        // 默认集群。广播模式下，消费组内的每一个消费者都会消费全量消息
        //consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.setConsumerGroup(rocketMqProperties.getConsumer().getTestTopic().getConsumerGroup());
        consumer.setConsumeMessageBatchMaxSize(rocketMqProperties.getConsumer().getTestTopic().getConsumerMessageBatchMaxSize());
        consumer.setConsumeThreadMin(rocketMqProperties.getConsumer().getTestTopic().getConsumerThreadMin());
        consumer.setConsumeThreadMax(rocketMqProperties.getConsumer().getTestTopic().getConsumerThreadMax());
        consumer.setConsumeFromWhere(rocketMqProperties.getConsumer().getTestTopic().getConsumerFromWhere());
        // 订阅主题，tag1 || tag2 || tag3 *代表全部订阅
        consumer.subscribe(rocketMqProperties.getConsumer().getTestTopic().getTopic(),
                rocketMqProperties.getConsumer().getTestTopic().getTag());
        return consumer;
    }
}
