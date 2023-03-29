package com.neil.redis.dao.impl;

import com.neil.aware.ServiceLocator;
import com.neil.redis.dao.RedisDefaultIDao;
import com.neil.redis.utils.ProtoStuffSerializeUtil;
import com.neil.redis.utils.SeriallizeUtil;
import com.neil.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Decription 默认的Redis操作类
 * @Author NEIL
 * @Date 2023/2/27 16:07
 * @Version 1.0
 */
@Component
public class RedisDefaultIDaoImpl implements RedisDefaultIDao {
    @Autowired
    private ProtoStuffSerializeUtil seriallizeUtil;

    @Autowired
    private RedisTemplate<String, byte[]> redisTemplate;

    @Override
    public int setEx(final String key, final Object value) {
        try {
            return (Integer) redisTemplate.execute((RedisCallback<Object>) connection -> {
                connection.setEx(key.getBytes(StandardCharsets.UTF_8), 1 * 24 * 60 * 60, seriallizeUtil.objectToByte(value));
                return 1;
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int setEx(final String key, final Object value, long time) {
        try {
            return (Integer) redisTemplate.execute((RedisCallback<Object>) connection -> {
                connection.setEx(key.getBytes(StandardCharsets.UTF_8), time, seriallizeUtil.objectToByte(value));
                return 1;
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public <T> T readObject(final String key, Class<T> clz) {
        T obj = null;
        obj = (T) redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] valueSrc = connection.get(key.getBytes(StandardCharsets.UTF_8));
            T value = null;
            if (valueSrc != null) {
                value = seriallizeUtil.byteToObject(valueSrc, clz);
            }
            return value;
        });
        return obj;
    }

    @Override
    public int delete(final String key) {
        long res = (long) redisTemplate.execute((RedisCallback) connection -> {
            return connection.del(key.getBytes(StandardCharsets.UTF_8));
        });
        return (int) res;
    }

    @Override
    public void lock(final String lock) {
        lock(lock, 60);
    }

    public void lock(final String lock, final long expire) {
        while (true) {
            if (tryLock(lock, expire)) {
                return;
            }
            Tools.sleep(10);
        }
    }

    @Override
    public boolean tryLock(final String lock, final long expire) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            byte[] lockBytes = lock.getBytes(StandardCharsets.UTF_8);
            boolean locked = connection.setNX(lockBytes, lockBytes);
            connection.expire(lockBytes, expire);
            return locked;
        });
    }

    @Override
    public void unLock(String lock) {
        delete(lock);
    }

}
