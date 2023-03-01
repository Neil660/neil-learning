package com.spring.boot.redis.dao;

import java.io.UnsupportedEncodingException;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/2/27 16:07
 * @Version 1.0
 */
public interface RedisDefaultIDao {
    public void setObj(final String key, final Object value) throws UnsupportedEncodingException;
    public Object getObj(final String key);

}
