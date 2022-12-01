package com.spring.boot.rocketmq.producers.producer;

import com.alibaba.fastjson.JSON;
import com.spring.boot.rocketmq.producers.message.MessageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.MQHelper;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingTooMuchRequestException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static com.spring.boot.rocketmq.producers.producer.SendMsgType.ASYNC;
import static com.spring.boot.rocketmq.producers.producer.SendMsgType.ONT_WAY;
import static com.spring.boot.rocketmq.producers.producer.SendMsgType.SYNC;

/**
 * @Decription 生产者
 * @Author NEIL
 * @Date 2022/11/28 15:19
 * @Version 1.0
 */
@Slf4j
public class RocketMqProducer {
    // 核心producer，真正发消息的对象
    private DefaultMQProducer producer;
    private String namesrvAddr;
    private String producerGroup;
    private String namespace;

    /**
     * 当消息发送异常时,延时重新发送的时间
     */
    private long delayTimeMillsWhenSendEx = 5000L;
    /**
     * 最大的重试次数
     */
    private int maxReSendTimeWhenSendEx = 5;

    private final ScheduledExecutorService scheduledExecutorService;
    public RocketMqProducer() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "IsapMessageSendExceptionScheduledThread");
                    }
                }
        );
    }

    public void init() {
        try {
            log.info("producer init...");
            producer = new DefaultMQProducer(producerGroup);
            producer.setNamesrvAddr(namesrvAddr);
            log.info(String.format("producer namesrvAddr is %s", namesrvAddr));
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
        this.scheduledExecutorService.shutdown();
    }

    public Object send(String topic, String tag, String msg) throws Exception {
        return this.send(new MessageVo(topic, tag, msg, 2, SYNC));
    }

    public Object send(MessageVo messageVo) throws Exception {
        if (maxReSendTimeWhenSendEx != 0 && messageVo.getSendTime() >= maxReSendTimeWhenSendEx) {
            log.info("Send time over max allow resend time, so the message will be ignore." + JSON.toJSONString(messageVo));
            return null;
        }
        Message msg = new Message(messageVo.getTopic(),
                messageVo.getTag(),
                messageVo.getMsg().getBytes(RemotingHelper.DEFAULT_CHARSET));
        if (messageVo.getIsDelay()) {
            msg.setDelayTimeLevel(messageVo.getDelayLevel());
        }

        SendResult sendResult = null;
        switch (messageVo.getMsgType()) {
            case SYNC:
                sendResult = (SendResult) this.SyncSendMsg(msg);
                break;
            case ASYNC:
                this.AsyncSendMsg(msg);
                break;
            case ONT_WAY:
                this.sendMsgOneWay(msg);
                break;
                default:
        }
        return sendResult;
    }

    /**
     * 同步消息发送
     * @return
     * @throws Exception
     */
    public Object SyncSendMsg(Message msg) throws Exception {
        SendResult sendResult = null;
        try {
            sendResult = producer.send(msg);
        }
        catch (MQBrokerException | RemotingTooMuchRequestException e) {//由于 mq broker 导致的发送异常,消息将尝试延迟进行再次发送
            log.error("", e.getMessage(), e);
        }

        return sendResult;
    }

    /**
     * 异步消息发送
     * @return
     * @throws Exception
     */
    public void AsyncSendMsg(Message msg) throws Exception {
        try {
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("Sending msg success.msgId is {}", sendResult.getMsgId());
                }
                @Override
                public void onException(Throwable e) {
                    log.error("Sending msg fail.", e.getMessage(), e);
                }
            });
        }
        catch (RemotingTooMuchRequestException e) {
            log.error("", e.getMessage(), e);
        }
    }

    /**
     * 单向模式发送
     * @throws Exception
     */
    public void sendMsgOneWay(Message msg) throws Exception {
        try {
            // 由于在oneway方式发送消息时没有请求应答处理，如果出现消息发送失败，则会因为没有重试而导致数据丢失
            // 若数据不可丢，建议选用可靠同步或可靠异步发送方式。
            producer.sendOneway(msg);
        }
        catch (RemotingTooMuchRequestException e) {
            log.error("", e.getMessage(), e);
        }
    }

    /**
     * 批量消息发送
     * @param msgs
     * @throws Exception
     */
    public void batchSendMsg(List<Message> msgs) throws Exception {
        try {
            producer.send(msgs);
        }
        catch (RemotingTooMuchRequestException e) {
            log.error("", e.getMessage(), e);
        }
    }

    /**
     * 顺序消息发送
     * @param message
     * @param queueKey 队列ID
     * @return
     * @throws Exception
     */
    private Object sendOrderlyMsg(Message message, int queueKey) throws Exception {
        //增加多租户支持
        //MQHelper.wrapNamespace(namespace, msg);
        SendResult sendResult = null;
        try {
            sendResult = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    int id = Math.abs((int) arg);
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, queueKey);
        }
        catch (MQBrokerException | RemotingTooMuchRequestException e) {
            log.error("", e.getMessage(), e);
        }
        return sendResult;
    }

    public DefaultMQProducer getProducer() {
        return producer;
    }

    public void setProducer(DefaultMQProducer producer) {
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
}
