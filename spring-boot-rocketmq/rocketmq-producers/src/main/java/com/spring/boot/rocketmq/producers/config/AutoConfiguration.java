package com.spring.boot.rocketmq.producers.config;

import com.spring.boot.rocketmq.producers.producer.RocketMqProducer;
import com.spring.boot.rocketmq.producers.producer.TransactionProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
     * 通过自动配置，导入生产者对象以及设置相关属性
     * @param rocketMqProperties
     * @return
     */
    @Conditional(RocketMQConditional.class)
    @ConditionalOnProperty(prefix = "sbr.rp.rocketmq.producer", value = {"enabled"}, matchIfMissing = true, havingValue = "true")
    @Bean(name = "rocketMQProducer", initMethod = "init", destroyMethod = "destroy")
    public RocketMqProducer getRocketMQProducer(RocketMqProperties rocketMqProperties) {
        RocketMqProducer producer = new RocketMqProducer();
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        //多租户
        //producer.setProducerGroup(MQHelper.wrapProducerGroup(isapMqProperties.getNamespace(), isapMqProperties.getProducer().getProducerGroup()));
        producer.setProducerGroup(rocketMqProperties.getProducer().getProducerGroup());
        producer.setDelayTimeMillsWhenSendEx(rocketMqProperties.getProducer().getDelayTimeMillsWhenSendEx());
        producer.setMaxReSendTimeWhenSendEx(rocketMqProperties.getProducer().getMaxReSendTimeWhenSendEx());
        return producer;
    }

    /**
     * 事务生产者
     * @param rocketMqProperties
     * @return
     */
    @Conditional(RocketMQConditional.class)
    @ConditionalOnProperty(prefix = "sbr.rp.rocketmq.producer", value = {"enabled"}, matchIfMissing = true, havingValue = "true")
    @Bean(name = "transactionProducer", initMethod = "init", destroyMethod = "destroy")
    public TransactionProducer getTransactionProducer(RocketMqProperties rocketMqProperties) {
        TransactionListener transactionListener = new TransactionListenerImpl();
        TransactionProducer producer = new TransactionProducer();
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        producer.setProducerGroup(rocketMqProperties.getProducer().getProducerGroup());
        producer.setDelayTimeMillsWhenSendEx(rocketMqProperties.getProducer().getDelayTimeMillsWhenSendEx());
        producer.setMaxReSendTimeWhenSendEx(rocketMqProperties.getProducer().getMaxReSendTimeWhenSendEx());
        producer.setTransactionListener(transactionListener);
        return producer;
    }

    static class TransactionListenerImpl implements TransactionListener {
        private AtomicInteger transactionIndex = new AtomicInteger(0);

        private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

        // 半事务消息发送成功后，执行本地事务的方法
        @Override
        public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
            int value = transactionIndex.getAndIncrement();
            int status = value % 3;
            localTrans.put(msg.getTransactionId(), status);
            return LocalTransactionState.UNKNOW;
        }

        // 二次确认消息没有收到，Broker端回查事务状态的方法
        @Override
        public LocalTransactionState checkLocalTransaction(MessageExt msg) {
            Integer status = localTrans.get(msg.getTransactionId());
            if (null != status) {
                switch (status) {
                    case 0:
                        return LocalTransactionState.UNKNOW;
                    case 1:
                        return LocalTransactionState.COMMIT_MESSAGE;
                    case 2:
                        return LocalTransactionState.ROLLBACK_MESSAGE;
                    default:
                        return LocalTransactionState.COMMIT_MESSAGE;
                }
            }
            return LocalTransactionState.COMMIT_MESSAGE;
        }
    }
}
