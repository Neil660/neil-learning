package com.multi.thread.example.case1;

import com.multi.thread.annotation.Actor;
import com.multi.thread.annotation.ConcurrencyTest;
import com.multi.thread.annotation.Expect;
import com.multi.thread.annotation.Observer;
import com.multi.thread.annotation.Setup;
import com.multi.thread.utils.Tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/12/10 14:13
 * @Version 1.0
 */
@ConcurrencyTest
public class TestRunner {
    // Runtime.getRuntime().availableProcessors()：返回Java虚拟机可用的处理器数量
    /**
     * Semaphore：用于多个线程对多个共享资源的互斥使用；用于并发线程数的限流，相关方法：
     *     acquire()：从Semaphore获取一个许可，如无可用许可前将一直阻塞等待。可带参数获取指定数量的许可
     *     tryAcquire()：从Semaphore尝试获取一个许可，如无用许可直接返回false，不会阻塞。可带参数获取指定数量的许可
     *     tryAcquire(int permits, long timeout, TimeUnit unit)：指定时间内获取成功返回true，否则返回false
     *     release()：释放一个许可
     *     availablePermits：获取当前Semaphore可用的许可数量
     */
    private static final Semaphore FLOW_CONTROL = new Semaphore(Runtime.getRuntime().availableProcessors());

    private static final ExecutorService EXECUTOR_SERVICE = Executors
            .newCachedThreadPool(new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setPriority(Thread.MAX_PRIORITY);
                    t.setDaemon(false);
                    return t;
                }
            });

    private volatile boolean stop = false;
    private final AtomicInteger runs = new AtomicInteger(0);
    private final int iterations;
    private final int thinkTime;
    private final Method createHelper;
    private final Method observerMethod;
    private volatile Method setupMethod = null;
    private final Object testCase;
    private final SortedMap<Integer, ExpectInfo> expectMap;

    public TestRunner(Method createHelper, Method observerMethod, Method setupMethod, Object testCase) {
        this.createHelper = createHelper;
        this.observerMethod = observerMethod;
        this.setupMethod = setupMethod;
        this.testCase = testCase;
        this.expectMap = parseExpects(getExpects(observerMethod));
        ConcurrencyTest testCaseAnn = testCase.getClass().getAnnotation(ConcurrencyTest.class);
        iterations = testCaseAnn.iterations();
        thinkTime = testCaseAnn.thinkTime();

    }

    private static class ExpectInfo {
        public final String description;
        private final AtomicInteger counter;

        public ExpectInfo(String description) {
            this(description, 0);
        }

        public ExpectInfo(String description, int hitCount) {
            this.description = description;
            this.counter = new AtomicInteger(hitCount);
        }

        public int hit() {
            return counter.incrementAndGet();
        }

        public int count() {
            return counter.get();
        }

    }

    public static void runTest(Class<?> testCaseClazz) throws InstantiationException, IllegalAccessException {
        Object test = testCaseClazz.newInstance();
        Method createHelper = null;
        Method observerMethod = null;
        Method setupMethod = null;
        for (Method method : testCaseClazz.getMethods()) {
            if (method.getAnnotation(Actor.class) != null) {
                createHelper = method;
                continue;
            }
            if (method.getAnnotation(Observer.class) != null) {
                observerMethod = method;
                continue;
            }
            if (method.getAnnotation(Setup.class) != null) {
                setupMethod = method;
                continue;
            }
        }

        TestRunner runner = new TestRunner(createHelper, observerMethod, setupMethod, test);
        runner.doTest();
    }

    private static Expect[] getExpects(final Method observerMethod) {
        Observer observerAnn = observerMethod.getAnnotation(Observer.class);
        Expect[] expects = observerAnn.value();
        return expects;
    }

    private static SortedMap<Integer, ExpectInfo> parseExpects(final Expect[] expects) {
        SortedMap<Integer, ExpectInfo> map = new ConcurrentSkipListMap<Integer, ExpectInfo>();
        for (Expect expect : expects) {
            map.put(Integer.valueOf(expect.expected()), new ExpectInfo(expect.desc()));
        }
        return map;
    }

    protected void doTest() {
        // 开启一个线程创建实例
        Runnable createHelperTask = new Runnable() {
            @Override
            public void run() {
                try {
                    createHelper.invoke(testCase, new Object[]{});
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
                catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                finally {
                    FLOW_CONTROL.release(1);
                }

            }

        };

        // 开启一个线程调用consume方法
        Runnable observerTask = new Runnable() {
            @SuppressWarnings("unchecked")
            @Override
            public void run() {
                try {
                    int result = -1;
                    try {
                        result = Integer.valueOf(observerMethod.invoke(testCase,
                                new Object[]{}).toString());
                        ExpectInfo expectInfo = expectMap.get(Integer.valueOf(result));
                        if (null != expectInfo) {
                            expectInfo.hit();
                        }
                        else {
                            expectInfo = new ExpectInfo("unexpected", 1);
                            ((ConcurrentMap<Integer, ExpectInfo>) expectMap).putIfAbsent(result, expectInfo);
                        }
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                    catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
                finally {
                    FLOW_CONTROL.release(1);
                }
            }

        };

        /**
         * CountDownLatch：可以使当前线程阻塞，等待其他线程完成给定任务。相关方法：
         *     await()：阻塞当前线程，直到计数器为零为止；
         *     await(long timeout, TimeUnit unit)：可以指定阻塞时长；
         *     countDown()：计数器减1，如果计数达到零，释放所有等待的线程。
         *     getCount()：返回当前计数
         */
        CountDownLatch latch;
        while (!stop) {
            // 只开2个count
            latch = createLatch();
            // setupMethod=null，没地方用
            if (null != setupMethod) {
                try {
                    setupMethod.invoke(testCase, new Object[]{});
                }
                catch (Exception e) {
                    break;
                }
            }

            schedule(observerTask, latch);
            schedule(createHelperTask, latch);

            if (runs.incrementAndGet() >= iterations) {
                break;
            }
            if (thinkTime > 0) {
                Tools.randomPause(thinkTime);
            }

            try {
                latch.await();
            }
            catch (InterruptedException e) {
                ;
            }
        }

        EXECUTOR_SERVICE.shutdown();
        try {
            EXECUTOR_SERVICE.awaitTermination(2000, TimeUnit.MINUTES);
        }
        catch (InterruptedException e) {
            ;
        }
        report();
    }

    private static class DummyLatch extends CountDownLatch {
        public DummyLatch(int count) {
            super(count);
        }

        @Override
        public void await() throws InterruptedException {
        }

        @Override
        public boolean await(long timeout, TimeUnit unit)
                throws InterruptedException {
            return true;
        }

        @Override
        public void countDown() {
        }

        @Override
        public long getCount() {
            return 0;
        }
    }

    private CountDownLatch createLatch() {
        CountDownLatch latch;
        if (null != setupMethod) {
            latch = new CountDownLatch(2);
        }
        else {
            latch = new DummyLatch(2);
        }
        return latch;
    }

    protected void report() {
        ExpectInfo ei;
        StringBuilder sbd = new StringBuilder();
        sbd.append("\n\r<<Simple Concurrency Test Framework report>>:");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
        sbd.append("\n\r===========================" + sdf.format(new Date())
                + "=================================");
        for (Map.Entry<Integer, ExpectInfo> entry : expectMap.entrySet()) {
            ei = entry.getValue();
            sbd.append("\n\rexpected:" + entry.getKey() + "		occurrences:"
                    + ei.count() + "		==>" + ei.description);
        }
        sbd.append("\n\r=====================================END=============================================");
        System.out.println(sbd);
    }

    /**
     * 获取一个许可，启动线程，并执行任务，执行完后释放许可，并计数减1
     * @param task
     * @param latch
     */
    protected void schedule(final Runnable task, final CountDownLatch latch) {
        try {
            FLOW_CONTROL.acquire(1);
        }
        catch (InterruptedException e) {
            latch.countDown();
            return;
        }
        EXECUTOR_SERVICE.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                }
                finally {
                    FLOW_CONTROL.release(1);
                    latch.countDown();
                }
            }
        });
    }

}
