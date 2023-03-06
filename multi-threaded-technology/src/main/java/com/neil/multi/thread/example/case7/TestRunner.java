package com.neil.multi.thread.example.case7;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/1/30 17:25
 * @Version 1.0
 */
public class TestRunner {
    public static void main(String[] args) {
        boolean done = false;
        BlockingQueue<String> queue = new LinkedBlockingDeque<>();
        SemaphoreBasedChannel<String> channel = new SemaphoreBasedChannel(queue, 3);

        // 向channel推送的线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!done) {
                    try {
                        int i = new Random().nextInt(10000000);
                        System.out.println("put:" + i);
                        Thread.sleep(1000);
                        // 一次推10个，但是限流是3，所以put会阻塞知道被take掉
                        for (int j = 0; j < 10; j++) {
                            channel.put(String.valueOf(i) + j);
                        }
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        // 从channel获取的线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!done) {
                    try {
                        System.out.println("take:" + channel.take());
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
