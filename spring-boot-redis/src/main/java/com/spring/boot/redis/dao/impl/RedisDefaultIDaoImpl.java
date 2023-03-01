package com.spring.boot.redis.dao.impl;

import com.spring.boot.redis.dao.RedisDefaultIDao;
import com.spring.boot.redis.utils.SeriallizeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/2/27 16:07
 * @Version 1.0
 */
@Component
public class RedisDefaultIDaoImpl implements RedisDefaultIDao {
    private static SeriallizeUtil seriallizeUtil;

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

    @Override
    public int setEx(final String key, final Object value, long time) {
        return (Integer) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(key.getBytes(StandardCharsets.UTF_8), time, seriallizeUtil.objectToByte(value));
                return 1;
            }
        });
    }

    @Override
    public <T> T readObject(final String key, Class<T> clz) {
        T obj = null;
        obj = (T) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] valueSrc = connection.get(key.getBytes(StandardCharsets.UTF_8));
                T value = null;
                if (valueSrc != null) {
                    value = seriallizeUtil.byteToObject(valueSrc, clz);
                }
                return value;
            }
        });
        return obj;
    }


    public void setSeriallizeUtil(SeriallizeUtil seriallizeUtil) {
        this.seriallizeUtil = seriallizeUtil;
    }
}
