package com.neil.utils;

import com.neil.annotation.HighRequest;
import lombok.Builder.ObtainVia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Decription 高并发测试工具
 * @Author NEIL
 * @Date 2023/3/7 15:38
 * @Version 1.0
 */
@Slf4j
public class HighRequestPerSecondUtil {

    public static void runTest(Class<?> clazz, String methodName, Object... args) {
        ThreadPoolExecutor pool = null;
        try {
            Object obj = clazz.newInstance();
            Method[] methods = clazz.getMethods();
            Method target = null;
            for (Method method : methods) {
                if (method.getAnnotation(HighRequest.class) != null && methodName.equals(method.getName())) {
                    target = method;
                }
            }
            if (target == null) return;

            HighRequest highRequest = target.getAnnotation(HighRequest.class);
            int threadNum = highRequest.threadNum();
            pool = new ThreadPoolExecutor(threadNum, threadNum, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
            CompletionService<Long> completionService = new ExecutorCompletionService(pool);

            Long s = System.currentTimeMillis();
            Long all = 0L;
            final Method m = target;
            Callable task = () -> {
                m.invoke(obj, args);
                return System.currentTimeMillis() - s;
            };

            for (int i = 0; i < threadNum; i++) {
                completionService.submit(task);
            }
            for (int i = 0; i < threadNum; i++) {
                Future<Long> future = completionService.take(); // 阻塞获取任务返回
                all += future.get();
            }
            log.info("总请求：" + threadNum + ",总耗时：" + all + "ms,每个请求平均耗时：" + (all / threadNum) + "ms");
            log.info("本方法总耗时：" + (System.currentTimeMillis() - s) + "ms");
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        finally {
            pool.shutdown();

        }
    }
}
