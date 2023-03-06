package com.neil.multi.thread.example.case0;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/1/7 22:43
 * @Version 1.0
 */
public class TestRunner {
    private static boolean flag = false;
    private static int num = -1;
    final static TestRunner runner = new TestRunner();

    public static void main(String[] args) throws InterruptedException {
        runner.startThread1();
        Thread t2 = runner.startThread2();
        // main线程需要等待startThread2的处理结果，才能执行以下命令，否则num=-1
        t2.join();
        System.out.println("num=" + num);
    }

    // 等待线程
    public void startThread1() {
        new Thread() {
            @Override
            public void run() {
                try {
                    start1();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void start1() throws InterruptedException {
        synchronized (this) {
            int count = 0;
            while (!flag) {
                wait();
                System.out.println("thread1 is waiting!");
                Thread.sleep(1000);
                count++;
            }
            System.out.println("thread1 start...count=" + count);
            while (true) {
                Thread.sleep(1000);
                System.out.println("thread1 is running");
            }
        }
    }

    // 通知线程
    public Thread startThread2() {
        Thread t2 = new Thread() {
            @Override
            public void run() {
                System.out.println("thread2 start...");
                start2();
            }
        };
        t2.start();
        return t2;
    }

    public void start2() {
        synchronized (this) {
            flag = true;
            notify();
            num++;
        }
    }
}

