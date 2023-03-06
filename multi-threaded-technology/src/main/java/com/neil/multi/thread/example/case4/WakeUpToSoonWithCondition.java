package com.neil.multi.thread.example.case4;

import com.neil.multi.thread.utils.Tools;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Decription Condition解决过早唤醒问题
 * @Author NEIL
 * @Date 2023/1/12 10:35
 * @Version 1.0
 */
public class WakeUpToSoonWithCondition {
    private static final Lock lock = new ReentrantLock();
    private static final Condition condLock1 = lock.newCondition();
    private static final Condition condLock2 = lock.newCondition();

    // 共享变量
    private static boolean cond1 = false;
    private static boolean cond2 = false;

    protected static final Random random = new Random();

    /**
     * 作为更新条件cond1的通知线程
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                for (;;) {
                    lock.lock();
                    try {
                        cond1 = random.nextInt(100) < 5 ? true : false;
                        if (cond1) {
                            System.out.println("cond1=" + cond1);
                            condLock1.signalAll();
                        }
                    }
                    finally {
                        lock.unlock();
                    }
                    Tools.randomPause(500);
                }
            }
        };
        t1.setName("Thread-N1");
        t1.setDaemon(true); //不阻止程序的停止
        t1.start();

        Thread t3 = new Thread() {
            @Override
            public void run() {
                for (;;) {
                    lock.lock();
                    try {
                        cond2 = random.nextInt(100) < 5 ? true : false;
                        if (cond2) {
                            System.out.println("cond2=" + cond2);
                            condLock2.signalAll();
                        }
                    }
                    finally {
                        lock.unlock();
                    }
                    Tools.randomPause(500);
                }
            }
        };
        t3.setName("Thread-N3");
        t3.setDaemon(true); //不阻止程序的停止
        t3.start();

        W1();
        W2();
        W3();
    }

    /**
     * W1等待线程，使用保护条件cond1
     */
    private static void W1() {
        Thread w1 = new Thread() {
            @Override
            public void run() {
                lock.lock();
                try {
                    while (!cond1) {
                        try {
                            System.out.println("W1 is waiting...");
                            condLock1.await();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("W1 release.");
                }
                finally {
                    lock.unlock();
                }
            }
        };
        w1.setName("Thread-w1");
        w1.start();
    }

    /**
     * W2等待线程，使用保护条件cond1
     */
    private static void W2() {
        Thread w2 = new Thread() {
            @Override
            public void run() {
                lock.lock();
                try {
                    while (!cond1) {
                        try {
                            System.out.println("W2 is waiting...");
                            condLock1.await();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("W2 release.");
                }
                finally {
                    lock.unlock();
                }
            }
        };
        w2.setName("Thread-w2");
        w2.start();
    }

    /**
     * W3等待线程，使用保护条件cond2
     */
    private static void W3() {
        Thread w3 = new Thread() {
            @Override
            public void run() {
                lock.lock();
                try {
                    while (!cond2) {
                        try {
                            System.out.println("W3 is waiting...");
                            condLock2.await();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("W3 release.");
                }
                finally {
                    lock.unlock();
                }
            }
        };
        w3.setName("Thread-w3");
        w3.start();
    }
}
