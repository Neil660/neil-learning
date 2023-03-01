package com.spring.boot.redis.dao;

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

    int setEx(final String key, final Object value, long time);
    <T> T readObject(final String key, Class<T> clz);
}
