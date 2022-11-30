package com.spring.boot.rocketmq.producers.message;

import com.spring.boot.rocketmq.producers.producer.SendMsgType;
import lombok.Data;

/**
 * @Decription 消息对象
 * @Author NEIL
 * @Date 2022/11/28 15:22
 * @Version 1.0
 */
@Data
public class MessageVo {
    /**
     * topic
     */

    private String topic;
    /**
     * tag
     */
    private String tag;
    /**
     * 消息key
     */
    private String key;
    /**
     * 消息内容
     */
    private String msg;
    /**
     * 对应消息的发送次数
     */
    private int sendTime;

    /**
     * 排序Key
     */
    private int  orderlyKey;

    /**
     * 是否延迟，已经延迟等级1-18
     */
    private Boolean isDelay = false;
    private int delayLevel;

    private SendMsgType msgType;

    private boolean once;

    public MessageVo(String topic, String tag, String msg, int sendTime, SendMsgType msgType) {
        this.topic = topic;
        this.tag = tag;
        this.msg = msg;
        this.sendTime = sendTime;
        this.msgType = msgType;
    }

    public MessageVo(String topic, String tag, String key, String msg, int sendTime, int orderlyKey) {
        this.topic = topic;
        this.tag = tag;
        this.key = key;
        this.msg = msg;
        this.sendTime = sendTime;
        this.orderlyKey = orderlyKey;
    }
    public MessageVo(String topic, String tag, String key, String msg, int sendTime) {
        this.topic = topic;
        this.tag = tag;
        this.key = key;
        this.msg = msg;
        this.sendTime = sendTime;
    }

    public MessageVo(String topic, String tag, String key, String msg) {
        this.topic = topic;
        this.tag = tag;
        this.key = key;
        this.msg = msg;
    }

    public MessageVo() {

    }

    /**
     * 增加发送次数
     */
    public int addSendTime() {
        return ++this.sendTime;
    }

    public void setOnce(boolean once) {
        this.once = once;
    }

    public boolean getOnce() {
        return once;
    }
}
