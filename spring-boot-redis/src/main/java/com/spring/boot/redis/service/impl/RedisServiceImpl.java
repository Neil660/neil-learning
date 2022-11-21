package com.spring.boot.redis.service.impl;

import com.spring.boot.redis.exception.RedisOperException;
import com.spring.boot.redis.service.RedisService;
import com.spring.boot.redis.utils.Byte2ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Decription redis工具类
 * @Author NEIL
 * @Date 2022/11/20 12:15
 * @Version 1.0
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    private static final long DEFAULT_EXPIRE_TIME = 60;
    private static final int storeDays = 7; // 默认保存7天
    private Boolean zipBoolean = true;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 如果锁空闲立即返回,获取失败 一直等待
     *
     * @param lock
     * @throws InterruptedException
     */
    public void lock(String lock) throws InterruptedException {
        while (true) {
            if (contestLock(lock)) {
                return;
            }
            Thread.sleep(10);
        }
    }

    /**
     * 释放锁
     *
     * @param lock
     * @throws Exception
     */
    public void unLock(String lock) throws RedisOperException {
        this.delete(lock);
        if (log.isDebugEnabled()) {
            log.debug("release lock[" + lock + "] successfully!");
        }
    }

    /**
     * 只会争取一次锁资源，则返回结果
     * @param lock
     * @return
     * @throws InterruptedException
     */
    public boolean contestLock(String lock) throws InterruptedException {
        if (log.isDebugEnabled()) {
            log.debug("","lock key: {}" , lock);
        }
        long i = this.acquireLock(lock, DEFAULT_EXPIRE_TIME);
        if (i == 1) { // 获取成功
            if (log.isDebugEnabled()) {
                log.debug("get lock[" + lock + "] successfully, expire in " + DEFAULT_EXPIRE_TIME + " seconds.");
            }
            return true;
        }
        else {
            log.warn("5042051",
                    "[" + lock + "] locked by another business, waiting 10 seconds will try lock again...");
        }
        return false;
    }

    /**
     * 获取共享锁(带超时时间)
     *
     * @return 1:获取锁成功,0获取锁失败
     */
    public long acquireLock(final String lockName, final long expire) {
        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        RedisConnection connection = connectionFactory.getConnection();
        byte[] lockBytes = lockName.getBytes(StandardCharsets.UTF_8);
        boolean locked = connection.setNX(lockBytes, lockBytes);
        connection.expire(lockBytes, expire);
        if (locked) {
            return 1L;
        }
        return 0L;
    }

    /**
     * 压缩
     * @param bytes
     * @return
     */
    public static byte[] zip(byte[] bytes) {
        ByteArrayOutputStream out = null;
        CompressorOutputStream cos = null;
        try {
            out = new ByteArrayOutputStream();
            cos = new GzipCompressorOutputStream(out);
            cos.write(bytes);
            cos.close();

            byte[] rs = out.toByteArray();
            out.close();
            return rs;
        }
        catch (IOException e) {
            log.error("", e.getMessage(), e);
        }
        finally {

        }
        return null;
    }

    /**
     * 写入一个对象
     * @param key
     * @param obj
     * @return
     * @throws RedisOperException
     */
    @Override
    public int setEx(final String key, final Object obj) throws RedisOperException {
        return (Integer) this.redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                if (zipBoolean) {
                    connection.setEx(key.getBytes(StandardCharsets.UTF_8), 60L * 60 * 24 * storeDays,
                            zip(Byte2ObjectUtil.objectToByte(obj)));
                }
                else {
                    connection.setEx(key.getBytes(StandardCharsets.UTF_8), 60L * 60 * 24 * storeDays,
                            Byte2ObjectUtil.objectToByte(obj));
                }
                return 1;
            }
        });
    }

    /**
     * 删除一个对象
     * @param key
     * @return
     * @throws RedisOperException
     */
    @Override
    public int delete(String key) throws RedisOperException {
        return ((Long) redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                return connection.del(key.getBytes(StandardCharsets.UTF_8));
            }
        })).intValue();
    }

    /**
     * 读取一个对象
     * @param key
     * @return
     * @throws RedisOperException
     */
    @Override
    public Object readObject(String key) throws RedisOperException {
        Object obj = redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] valueSrc = connection.get(key.getBytes(StandardCharsets.UTF_8));
                Object value = null;
                if (zipBoolean) {
                    // 解压缩
                    value = valueSrc == null ? null : Byte2ObjectUtil.byteToObject(Byte2ObjectUtil.uzip(valueSrc));
                }
                else {
                    value = valueSrc == null ? null : Byte2ObjectUtil.byteToObject(valueSrc);
                }
                return value;
            }
        });
        return obj;
    }

    /**
     * 泛型读取对象
     * @param key
     * @param clz
     * @param <T>
     * @return
     * @throws Exception
     */
    @Override
    public <T> T readObject(String key, Class<T> clz) throws Exception {
        T obj = (T) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] valueSrc = connection.get(key.getBytes(StandardCharsets.UTF_8));
                T value = null;
                if (zipBoolean) {
                    // 解压缩
                    value = valueSrc == null ? null : Byte2ObjectUtil.byteToObject(Byte2ObjectUtil.uzip(valueSrc), clz);
                }
                else {
                    value = valueSrc == null ? null : Byte2ObjectUtil.byteToObject(valueSrc, clz);
                }
                return value;
            }
        });
        return obj;
    }

    /**
     * 自增
     * @param key
     * @return
     * @throws RedisOperException
     */
    @Override
    public long incr(String key) throws RedisOperException {
        return ((Long) redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                long count = connection.incr(key.getBytes(StandardCharsets.UTF_8));
                connection.expire(key.getBytes(StandardCharsets.UTF_8), 24 * 60 * 60);// 一天计数器超时
                return count;
            }
        }));
    }

    /**
     * 自减
     * @param key
     * @return
     * @throws RedisOperException
     */
    @Override
    public long decr(String key) throws RedisOperException {
        return ((Long) redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                long count = connection.decr(key.getBytes(StandardCharsets.UTF_8));
                connection.expire(key.getBytes(StandardCharsets.UTF_8), 24 * 60 * 60);// 一天计数器超时
                return count;
            }
        }));
    }

    /**
     * hash类型的写入值
     * @param key
     * @param field
     * @param value
     * @throws RedisOperException
     */
    @Override
    public void hSet(String key, String field, String value) throws RedisOperException {
        final byte[] keyByte = key.getBytes(StandardCharsets.UTF_8);
        final byte[] fieldByte = field.getBytes(StandardCharsets.UTF_8);
        final byte[] valueByte = value.getBytes(StandardCharsets.UTF_8);
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                connection.hSet(keyByte, fieldByte, valueByte);
                connection.expire(keyByte, storeDays * 60L * 60 * 24);
                return null;
            }
        });
    }

    /**
     * hash类型写入对象
     * @param key
     * @param field
     * @param obj
     */
    @Override
    public void hSetObj(final String key, final String field, final Object obj) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                if (zipBoolean) {
                    connection.hSet(key.getBytes(StandardCharsets.UTF_8), field.getBytes(StandardCharsets.UTF_8),
                            zip(Byte2ObjectUtil.objectToByte(obj)));
                }
                else {
                    connection.hSet(key.getBytes(StandardCharsets.UTF_8), field.getBytes(StandardCharsets.UTF_8),
                            Byte2ObjectUtil.objectToByte(obj));
                }
                return null;
            }
        });
    }

    /**
     * hash类型读取值
     * @param key
     * @param field
     * @param value
     * @throws RedisOperException
     */
    @Override
    public String hGet(String key, String field, String value) throws RedisOperException {
        final byte[] keyByte = key.getBytes(StandardCharsets.UTF_8);
        final byte[] fieldByte = field.getBytes(StandardCharsets.UTF_8);
        return (String) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) {
                byte[] value = connection.hGet(keyByte, fieldByte);
                if (value != null) {
                    String result = new String(value, StandardCharsets.UTF_8);
                    return result;
                }
                return null;
            }
        });
    }

    /**
     * hash类型读取对象
     * @param key
     * @param field
     * @param value
     * @throws RedisOperException
     */
    @Override
    public Object hGetObj(String key, String field, String value) throws RedisOperException {
        Object obj = null;
        obj = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                // 期望list
                byte[] valueSrc = connection.hGet(key.getBytes(StandardCharsets.UTF_8), field.getBytes(StandardCharsets.UTF_8));
                Object value = null;
                if (zipBoolean) {
                    // 解压缩
                    value = valueSrc == null ? null : Byte2ObjectUtil.byteToObject(Byte2ObjectUtil.uzip(valueSrc));
                }
                else {
                    value = valueSrc == null ? null : Byte2ObjectUtil.byteToObject(valueSrc);
                }
                return value;
            }
        });

        return obj;
    }

    /**
     * hash类型删除值
     * @param key
     * @param field
     * @throws Exception
     */
    @Override
    public void hDel(final String key, final String field) throws RedisOperException {
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                return connection.hDel(key.getBytes(StandardCharsets.UTF_8), field.getBytes(StandardCharsets.UTF_8));
            }
        });
    }

    @Override
    public Boolean hasExists(String key) throws RedisOperException {
        return redisTemplate.hasKey(key);
    }
}
