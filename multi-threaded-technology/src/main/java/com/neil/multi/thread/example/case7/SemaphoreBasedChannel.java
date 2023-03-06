package com.neil.multi.thread.example.case7;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/1/30 17:25
 * @Version 1.0
 */
public class SemaphoreBasedChannel<P> implements Channel<P> {
    private final BlockingQueue<P> queue;
    private final Semaphore semaphore;

    /**
     * @param queue     阻塞队列，通常是一个无界阻塞队列。
     * @param flowLimit 流量限制数
     */
    public SemaphoreBasedChannel(BlockingQueue<P> queue, int flowLimit) {
        this(queue, flowLimit, false);
    }

    public SemaphoreBasedChannel(BlockingQueue<P> queue, int flowLimit,
                                 boolean isFair) {
        this.queue = queue;
        // 设置限流的数目flowLimit
        this.semaphore = new Semaphore(flowLimit, isFair);
    }

    @Override
    public P take() throws InterruptedException {
        return queue.take();
    }

    @Override
    public void put(P product) throws InterruptedException {
        semaphore.acquire(); // 申请一个配额
        try {
            queue.put(product); // 访问虚拟资源
        }
        finally {
            semaphore.release(); // 返还一个配额
        }
    }
}
