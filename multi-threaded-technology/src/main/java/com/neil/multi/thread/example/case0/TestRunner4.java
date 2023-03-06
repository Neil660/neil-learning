package com.neil.multi.thread.example.case0;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Decription 守护线程与用户线程
 * @Author NEIL
 * @Date 2023/1/28 11:03
 * @Version 1.0
 */
public class TestRunner4 {
    private static boolean flag = true;

    public static void main(String[] args) throws Exception {
        int error = 0;
        int all = 10000;
        long s = System.currentTimeMillis();
        for (int i = 0; i < all; i++) {
            TestRunner2 test = new TestRunner2();
            int sum = test.test();
            if (sum != 50005000) {
                System.out.println(sum);
                error++;
            }
        }
        System.out.println("总执行：" + all + "，错误：" + error + "，耗时：" + (System.currentTimeMillis() - s));

        final ExecutorService es = Executors.newSingleThreadExecutor();
        // 异步任务批量执行
        final CompletionService<String> completionService = new ExecutorCompletionService<>(es);
        for (int i = 0; i < 10; i++) {
            completionService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return "0";
                }
            });
        }
        for (int i = 0; i < 10; i++) {
            Future<String> future = completionService.take(); // 阻塞获取任务返回
            String res = future.get();
        }
    }

    public static void startDaemonThread() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (flag) {
                        // do something
                        Thread.sleep(1000);
                        System.out.println("Daemon-Thread is starting...");
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //t.setDaemon(true);
        t.setName("Daemon-thread");
        t.start();
    }

    public static void startUserThread() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(1000);
                        System.out.println("User-Thread is starting...");
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.setName("User-thread");
        t.start();
    }
}
