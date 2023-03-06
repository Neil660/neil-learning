package com.neil.multi.thread.example.case10;

import com.neil.multi.thread.utils.Debug;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @Decription 定期搜索死锁线程并发送中断
 * @Author NEIL
 * @Date 2023/2/2 16:15
 * @Version 1.0
 */
public class DeadlockDetector extends Thread {
    static final ThreadMXBean tmb = ManagementFactory.getThreadMXBean();
    /**
     * 检测周期（单位为毫秒）
     */
    private final long monitorInterval;

    public DeadlockDetector(long monitorInterval) {
        super("DeadLockDetector");
        setDaemon(true);
        this.monitorInterval = monitorInterval;
    }

    public DeadlockDetector() {
        this(2000);
    }

    public static ThreadInfo[] findDeadlockedThreads() {
        // 搜索死锁线程id集合
        long[] ids = tmb.findDeadlockedThreads();
        return null == tmb.findDeadlockedThreads() ?
                new ThreadInfo[0] : tmb.getThreadInfo(ids);
    }

    public static Thread findThreadById(long threadId) {
        // 通过线程ID找到线程
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread.getId() == threadId) {
                return thread;
            }
        }
        return null;
    }

    public static boolean interruptThread(long threadID) {
        Thread thread = findThreadById(threadID);
        if (null != thread) {
            // 对指定线程发送中断标志
            thread.interrupt();
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        ThreadInfo[] threadInfoList;
        ThreadInfo ti;
        int i = 0;
        try {
            for (; ; ) {
                // 检测系统中是否存在死锁
                threadInfoList = DeadlockDetector.findDeadlockedThreads();
                if (threadInfoList.length > 0) {
                    // 选取一个任意的死锁线程
                    ti = threadInfoList[i++ % threadInfoList.length];
                    Debug.error("Deadlock detected,trying to recover"
                                    + " by interrupting%n thread(%d,%s)%n",
                            ti.getThreadId(),
                            ti.getThreadName());
                    // 给选中的死锁线程发送中断
                    DeadlockDetector.interruptThread(ti.getThreadId());
                    continue;
                }
                else {
                    Debug.info("No deadlock found!");
                    i = 0;
                }
                Thread.sleep(monitorInterval);
            }// for循环结束
        }
        catch (InterruptedException e) {
            // 什么也不做
            ;
        }
    }
}