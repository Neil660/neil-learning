package com.neil.redis.service;

import com.neil.redis.exception.RedisOperException;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/11/20 12:15
 * @Version 1.0
 */
public interface RedisService {
    int setEx(final String key, final Object value) throws RedisOperException;

    int delete(final String key) throws RedisOperException;

    Object readObject(final String key) throws RedisOperException;

    <T> T readObject(final String key, Class<T> clz) throws Exception;

    long incr(final String key) throws RedisOperException;

    long decr(final String key) throws RedisOperException;

    void hSet(String key, String field, String value) throws RedisOperException;
    void hSetObj(String key, String field, Object obj) throws RedisOperException;
    String hGet(String key, String field, String value) throws RedisOperException;
    Object hGetObj(String key, String field, String value) throws RedisOperException;
    void hDel(final String key, final String field) throws RedisOperException;

    Boolean hasExists(String key) throws RedisOperException;
}
