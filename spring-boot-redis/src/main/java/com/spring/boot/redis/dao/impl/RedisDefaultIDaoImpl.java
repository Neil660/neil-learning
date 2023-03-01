package com.spring.boot.redis.dao.impl;

import com.spring.boot.redis.dao.RedisDefaultIDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/2/27 16:07
 * @Version 1.0
 */
@Component
public class RedisDefaultIDaoImpl implements RedisDefaultIDao {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setObj(final String key, final Object value) {
        redisTemplate.opsForValue().set(key, value.toString());
    }

    @Override
    public Object getObj(final String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
