package com.multi.thread.example.case5;

import com.multi.thread.utils.Debug;
import com.multi.thread.utils.Tools;

import java.util.concurrent.CountDownLatch;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/1/12 18:55
 * @Version 1.0
 */
public class TestRunner {
    private static final CountDownLatch latch = new CountDownLatch(4);
    private static int data;

    public static void main(String[] args) throws InterruptedException {
        // CountDownLatch基本使用
        Thread workerThread = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread is starting...");
                for (int i = 1; i <= 10; i++) {
                    data = i;
                    // 计数器到0后可以继续countDown，不会报错，一直跑到i=10
                    latch.countDown();
                    // 使当前线程暂停（随机）一段时间
                    Tools.randomPause(1000);
                }

            }

            ;
        };
        workerThread.start();

        latch.await();
        /**
         * 这里的data是4而不是10
         * 首先，latch.countDown()被workerThread执行了4次之后，main线程对latch.await()的调用就返回了，从而使该线程被唤醒。
         * 其次，workerThread在执行latch.countDown()前所执行的操作（更新共享变量data）的结果对等待线程（main线程）
         *     从latch.await()返回之后的代码可见，因此main线程被唤醒时能够读取到此前workerThread在latch.countDown()
         *     调用返回前的操作结果——data被更新为4。
         */
        System.out.println("Thread release.data=" + data);
    }
}
