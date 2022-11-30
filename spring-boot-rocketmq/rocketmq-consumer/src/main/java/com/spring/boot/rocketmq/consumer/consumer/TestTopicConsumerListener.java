package com.spring.boot.rocketmq.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/11/29 15:28
 * @Version 1.0
 */
@Slf4j
public class TestTopicConsumerListener implements MessageListenerConcurrently {
    /**
     * 用来统计mq消费者处于running状态的工作线程,需要保证该值比消费者配置的最大线程数大
     */
    private int maxNumOfRunningConsumeThread = 10000;//一个很大的值

    /**
     * 停止超时时间,默认为5分钟
     */
    private long shutdownTimeout = 5 * 60 * 60;//单位(毫秒)

    /**
     * 用于统计当前mq消费者处于active状态的工作线程
     */
    private Semaphore semaphore = new Semaphore(maxNumOfRunningConsumeThread);

    /**
     * 是否已经暂停mq消费
     */
    private volatile boolean isStop = false;

    /**
     * 当应用状态已设置为停止时,相应工作线程的休眠时间
     */
    private long workThreadSleepTimeMillsWhenStop = 5000L;

    /**
     * 最近一次consume later的时间戳,暂时的作用是用来判断在停止过程中是否还有本地缓存的消息
     */
    private volatile long lastConsumeLaterTime;

    /**
     * 在停止过程中,各个消息被RECONSUME_LATER处理的间隔时间
     */
    private long msgReConsumeIntervalWhenStop = 500L;

    private AtomicLong consumerLaterMqCount = new AtomicLong(0);

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        // 由于并发消费模式mq消息在本地是存在缓存(默认本地)的,为了保证本地的消费进度会被更新,防止Ack卡进度
        // 需保证本地所有的消息都被消费完成(CONSUME_SUCCESS or RECONSUME_LATER)
        // ACK机制原理可参考链接: https://jaskey.github.io/blog/2017/01/25/rocketmq-consume-offset-management/
        if (isStop) {
            lastConsumeLaterTime = System.currentTimeMillis();
            for (MessageExt message : msgs) {
                consumerLaterMqCount.incrementAndGet();
                log.info("consumer has been stopped, mq msg: " + new String(message.getBody(), Charsets.UTF_8)
                        + " will be consumed later!");
            }
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }

        boolean isSuccessAcqLock = false;
        try { // 消费消息之前先获取锁，为了停止时确认当前正在运行的消费者线程的个数
            isSuccessAcqLock = semaphore.tryAcquire(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            log.error("", e.getLocalizedMessage(), e);
        }

        if (!isSuccessAcqLock) {//无法获取锁,该情景几乎不可能发生.
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }

        try {
            //消费消息
            for (MessageExt msg : msgs) {
                if (msg == null) {
                    continue;
                }
                String msgBody = new String(msg.getBody(), Charsets.UTF_8);
                if ("".equals(msgBody)) {
                    continue;
                }
                log.info("Receivce a new msg:" + msgBody);
                // do something
            }
        }
        finally {
            semaphore.release();
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    public void shutdown() {
        log.info("Waiting for [" + (maxNumOfRunningConsumeThread - semaphore.availablePermits())
                + "]'s consume thread execution complete, Shutdown timeout(ms): "
                + shutdownTimeout);
        this.isStop = true;
        long s = System.currentTimeMillis();
        try {//1. 保证正在处理消息的线程处理完成
            semaphore.tryAcquire(maxNumOfRunningConsumeThread, shutdownTimeout, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException e) {
            log.error("", e.getLocalizedMessage(), e);
        }
        finally {
            semaphore.release(maxNumOfRunningConsumeThread);
        }

        //2. 确保本地缓存的未被消费的消息被 RECONSUME_LATER
        while (!Thread.interrupted()) {
            //XXX: 当超过msgReConsumeIntervalWhenStop时间未有消息被reconsume later处理,即可视为本地没有缓存消息.
            if (System.currentTimeMillis() - lastConsumeLaterTime > msgReConsumeIntervalWhenStop) {
                break;
            }
            log.info("Waiting local message to be consume later success.");
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                log.error("", e.getLocalizedMessage(), e);
            }
        }
        log.info("consumer later total cost time(ms): " + (System.currentTimeMillis() - s) +
                " total mq num: " + consumerLaterMqCount.get());
        log.info("Consumer [testConsumerGroup]: all consume thread have run to complete! cost time(ms): "
                + (System.currentTimeMillis() - s));
    }

    public int getMaxNumOfRunningConsumeThread() {
        return maxNumOfRunningConsumeThread;
    }

    public void setMaxNumOfRunningConsumeThread(int maxNumOfRunningConsumeThread) {
        this.maxNumOfRunningConsumeThread = maxNumOfRunningConsumeThread;
    }

    public long getShutdownTimeout() {
        return shutdownTimeout;
    }

    public void setShutdownTimeout(long shutdownTimeout) {
        this.shutdownTimeout = shutdownTimeout;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public long getWorkThreadSleepTimeMillsWhenStop() {
        return workThreadSleepTimeMillsWhenStop;
    }

    public void setWorkThreadSleepTimeMillsWhenStop(long workThreadSleepTimeMillsWhenStop) {
        this.workThreadSleepTimeMillsWhenStop = workThreadSleepTimeMillsWhenStop;
    }

    public long getLastConsumeLaterTime() {
        return lastConsumeLaterTime;
    }

    public void setLastConsumeLaterTime(long lastConsumeLaterTime) {
        this.lastConsumeLaterTime = lastConsumeLaterTime;
    }

    public long getMsgReConsumeIntervalWhenStop() {
        return msgReConsumeIntervalWhenStop;
    }

    public void setMsgReConsumeIntervalWhenStop(long msgReConsumeIntervalWhenStop) {
        this.msgReConsumeIntervalWhenStop = msgReConsumeIntervalWhenStop;
    }

    public AtomicLong getConsumerLaterMqCount() {
        return consumerLaterMqCount;
    }

    public void setConsumerLaterMqCount(AtomicLong consumerLaterMqCount) {
        this.consumerLaterMqCount = consumerLaterMqCount;
    }
}
