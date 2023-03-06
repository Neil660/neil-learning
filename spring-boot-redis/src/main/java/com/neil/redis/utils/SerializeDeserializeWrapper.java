package com.neil.redis.utils;

/**
 * @Decription 序列化/反序列化对象包装类
 * @Author NEIL
 * @Date 2023/3/1 11:23
 * @Version 1.0
 */
public class SerializeDeserializeWrapper<T> {
    private T data;

    public static <T> SerializeDeserializeWrapper<T> builder(T data) {
        SerializeDeserializeWrapper<T> wrapper = new SerializeDeserializeWrapper<>();
        wrapper.setData(data);
        return wrapper;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

