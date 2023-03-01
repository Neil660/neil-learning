package com.spring.boot.redis.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/1 14:34
 * @Version 1.0
 */
@Slf4j
public class Tools {
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
}
