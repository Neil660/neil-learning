package com.spring.boot.rocketmq.producers.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Decription 事务生产者
 * @Author NEIL
 * @Date 2022/11/29 14:54
 * @Version 1.0
 */
@Slf4j
public class TransactionProducer {
    private TransactionMQProducer producer;
    private String namesrvAddr;
    private String producerGroup;
    private String namespace;
    private TransactionListener transactionListener;

    /**
     * 当消息发送异常时,延时重新发送的时间
     */
    private long delayTimeMillsWhenSendEx = 5000L;
    /**
     * 最大的重试次数
     */
    private int maxReSendTimeWhenSendEx = 5;

    private final ExecutorService executorService;
    public TransactionProducer() {
        executorService = new ThreadPoolExecutor(2,
                5,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2000),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("client-transaction-msg-check-thread");
                        return thread;
                    }
                }
        );
    }

    public void init() {
        try {
            log.info("producer init...");
            producer = new TransactionMQProducer(producerGroup);
            producer.setNamesrvAddr(namesrvAddr);
            producer.setExecutorService(executorService);
            producer.setTransactionListener(transactionListener);
            log.info("producer namesrvAddr is {}", namesrvAddr);
            producer.start();
            log.info("producer init success!");
        }
        catch (Exception e) {
            log.error("Rocket mq Producer init exception!", e.getMessage(), e);
        }
    }

    public void destroy() {
        if (producer != null) {
            producer.shutdown();
        }
        this.executorService.shutdown();
    }

    public Object sendMsg(Message msg) throws Exception {
        SendResult sendResult = null;
        try {
            sendResult = producer.sendMessageInTransaction(msg, null);
        }
        catch (MQClientException e) {
            log.error("", e.getMessage(), e);
        }
        return sendResult;
    }

    public TransactionMQProducer getProducer() {
        return producer;
    }

    public void setProducer(TransactionMQProducer producer) {
        this.producer = producer;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getProducerGroup() {
        return producerGroup;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public long getDelayTimeMillsWhenSendEx() {
        return delayTimeMillsWhenSendEx;
    }

    public void setDelayTimeMillsWhenSendEx(long delayTimeMillsWhenSendEx) {
        this.delayTimeMillsWhenSendEx = delayTimeMillsWhenSendEx;
    }

    public int getMaxReSendTimeWhenSendEx() {
        return maxReSendTimeWhenSendEx;
    }

    public void setMaxReSendTimeWhenSendEx(int maxReSendTimeWhenSendEx) {
        this.maxReSendTimeWhenSendEx = maxReSendTimeWhenSendEx;
    }

    public TransactionListener getTransactionListener() {
        return transactionListener;
    }

    public void setTransactionListener(TransactionListener transactionListener) {
        this.transactionListener = transactionListener;
    }
}
