package com.neil.multi.thread.example.case0;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/1/11 15:01
 * @Version 1.0
 */
public class TestRunner2 {
    private static AtomicInteger sum = new AtomicInteger(0);
    private static volatile Integer sum1 = new Integer(0);

    public TestRunner2() {
        sum.set(0);
        sum1 = new Integer(0);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(sum());
    }

    private final static ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(5, 10,
                    2 * 60L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());


    public static int sum() throws InterruptedException, ExecutionException {
        int sum = 0;
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 1; i <= 10; i++) {
            Future<Integer> result = EXECUTOR_SERVICE.submit(new CountSumThread((i - 1) * 1000 + 1, i * 1000, latch));
            sum += result.get();
        }
        latch.await();
        //EXECUTOR_SERVICE.shutdown();
        return sum;
    }

    static class CountSumThread implements Callable<Integer> {
        int l;
        int r;
        CountDownLatch latch;

        CountSumThread(int l, int r, CountDownLatch latch) {
            this.l = l;
            this.r = r;
            this.latch = latch;
        }

        @Override
        public Integer call() throws Exception {
            int sum = 0;
            try{
                for (int i = l; i <= r ; i++) {
                    sum += i;
                }
            }finally {
                latch.countDown();
            }

            return sum;
        }
    }

    public int test() throws Exception {
        int n = 10; // 10个线程
        Thread[] tall = new Thread[10];
        for (int i = n;i > 0;i--) {
            tall[i - 1] = TestRunner2.count((i - 1) * 1000 + 1, i * 1000);
        }
        for (int i = 0; i < n; i++) {
            tall[i].join();
        }
        return sum.get();
    }

    private static Future<Integer> execute(int l, int r) {
        FutureTask<Integer> fu = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int sum = 0;
                for (int i = l; i <= r; i++) {
                    sum += i;
                }
                return sum;
            }
        });
        fu.run();
        return fu;
    }

    public void show() {
        FutureTask<String> ft = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "";
            }
        });
        Executor pool = Executors.newSingleThreadExecutor();
        pool.execute(ft); // 交给Executor执行，以支持异步执行
        ft.run(); // 也可以直接运行
    }

    private static Thread count(int l, int r) {
        Thread t = new Thread() {
          @Override
          public void run() {
              int all = 0;
              for (int i = l;i <= r;i++) {
                  all += i;
              }
              //sum.addAndGet(all);
              Integer i = new Integer(all);
              sum1 = i;
          }
        };
        t.start();
        return t;
    }
}
