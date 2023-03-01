package com.spring.boot.redis.utils;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/1 14:10
 * @Version 1.0
 */
public interface SeriallizeUtil {
    <T> T byteToObject(byte[] data, Class<T> clazz);
    <T> byte[] objectToByte(T obj);
}
