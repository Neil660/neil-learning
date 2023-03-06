package com.neil.redis.dao;

import java.io.UnsupportedEncodingException;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/2/27 16:07
 * @Version 1.0
 */
public interface RedisDefaultIDao {
    void setObj(final String key, final Object value) throws UnsupportedEncodingException;
    Object getObj(final String key);

    /**
     * 缓存一个对象带超时时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    int setEx(final String key, final Object value, long time);

    /**
     * 读取一个对象
     * @param key
     * @param clz
     * @param <T>
     * @return
     */
    <T> T readObject(final String key, Class<T> clz);

    /**
     * 删除一个对象
     * @param key
     * @return
     */
    int delete(final String key);

    /**
     * 阻塞获取锁
     * @param lock
     */
    void lock(String lock);

    /**
     * 申请一次锁资源
     * @param lock
     * @return
     */
    boolean tryLock(String lock, long expire);

    /**
     * 释放锁
     * @param lock
     */
    void unLock(String lock);
}
