package com.neil.multi.thread.example.case0;

import com.neil.multi.thread.annotation.ConcurrencyTest;
import com.neil.utils.Tools;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class TestRunner2 {
    private static AtomicInteger sum = new AtomicInteger(0);
    private static volatile Integer sum1 = new Integer(0);
    private static int sum2 = 0;

    public TestRunner2() {
        sum.set(0);
        sum1 = new Integer(0);
    }

    public static void main(String[] args) throws Exception {
//        log.info("===========");
//        Callable c = () -> {
//            Tools.sleep(3000);
//            return 1;
//        };
//        Integer res = (Integer) c.call();
//        log.info(res + "");

        // 将和计算分到线程池里不同的线程执行，每执行一个任务才能执行下一个
        /*log.info("sum计算开始");
        log.info(sum() + "");
        log.info("sum计算结束");

        log.info("test计算开始");
        log.info(test() + "");
        log.info("test计算结束");*/

        log.info("同步计算开始");
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            final int fi = i;
            EXECUTOR_SERVICE.execute(() -> {
                int localSum = 0;
                for (int j = 1 + fi * (10000 / 10); j <= (fi + 1) * (10000 / 10); j++) {
                    localSum += j;
                }
                Tools.sleep(1000);
                synchronized (TestRunner2.class) {
                    sum2 += localSum;
                }
                latch.countDown();
            });
        }

        try {
            latch.await(); // 等待所有线程执行完毕
            log.info(sum2 + "");
            log.info("同步计算结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        EXECUTOR_SERVICE.shutdown();
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
            Tools.sleep(500);
            return sum;
        }
    }

    public static int test() throws Exception {
        int n = 10; // 10个线程
        Thread[] tall = new Thread[10];
        for (int i = n;i > 0;i--) {
            tall[i - 1] = TestRunner2.count((i - 1) * 1000 + 1, i * 1000);
        }
        for (int i = 0; i < n; i++) {
            tall[i].join();
        }
        return sum1;
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
              Tools.sleep(500);
              //sum.addAndGet(all);
              Integer i = new Integer(all);
              sum1 += i;
          }
        };
        t.start();
        return t;
    }
}
