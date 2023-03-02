package com.neil.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.util.Random;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/2 16:17
 * @Version 1.0
 */
@Slf4j
public class Tools {
    private static final Random rnd = new Random();

    /**
     * 关闭流不报异常
     * @param closeable
     */
    public static void silentClose(Closeable... closeable) {
        if (null == closeable) {
            return;
        }
        for (Closeable c : closeable) {
            if (null == c) {
                continue;
            }
            try {
                c.close();
            }
            catch (Exception ignored) {
                log.warn(ignored.getMessage(), ignored);
            }
        }
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
