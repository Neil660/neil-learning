package com.multi.thread.example.case9;

import com.multi.thread.utils.Debug;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/1/31 15:15
 * @Version 1.0
 */
public class TerminatableTaskRunner {
    protected final BlockingQueue<Runnable> channel;
    // 线程停止标记
    protected volatile boolean inUse = true;
    // 待处理任务计数器
    public final AtomicInteger reservations = new AtomicInteger(0);
    private volatile Thread workerThread;

    public TerminatableTaskRunner(BlockingQueue<Runnable> channel) {
        this.channel = channel;
        this.workerThread = new WorkerThread();
    }

    public TerminatableTaskRunner() {
        this(new LinkedBlockingQueue<Runnable>());
    }

    public void init() {
        final Thread t = workerThread;
        if (null != t) {
            t.start();
        }
    }

    public void submit(Runnable task) throws InterruptedException {
        channel.put(task);
        reservations.incrementAndGet();
    }

    public void shutdown() {
        Debug.info("Shutting down service...");
        inUse = false;// 语句①
        final Thread t = workerThread;
        if (null != t) {
            t.interrupt();// 语句②
        }
    }

    public void cancelTask() {
        Debug.info("Canceling in progress task...");
        workerThread.interrupt();
    }

    class WorkerThread extends Thread {
        @Override
        public void run() {
            Runnable task = null;
            try {
                for (; ; ) {
                    // 线程不再被需要，且无待处理任务
                    if (!inUse && reservations.get() <= 0) {// 语句③
                        break;
                    }
                    task = channel.take();
                    try {
                        // 1、task已经停止，此时就会抛异常InterruptedException，那么run返回，线程停止
                        // 2、如果在调用shutdown()那一刻，目标线程正在执行task.run()且task.run()中的代码清空了线程中断标记，
                        //     那么channel.take()调用无法抛出InterruptedException（因为中断标记被清除了）。但是会通过inUse
                        //     来实现停止线程
                        // 3、如果task.run()一直在执行
                        task.run();
                    }
                    catch (Throwable e) {
                        e.printStackTrace();
                    }
                    // 使待处理任务数减少1
                    reservations.decrementAndGet();// 语句④
                }
            }
            catch (InterruptedException e) {
                // 停止worker线程
                workerThread = null;
            }
            Debug.info("worker thread terminated.");
        }
    }
}
