package com.neil.redis.utils;

import com.google.common.collect.Maps;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Decription 基于ProtoStuff的序列化方式
 * @Author NEIL
 * @Date 2023/3/1 11:20
 * @Version 1.0
 */
@Slf4j
@Component
public class ProtoStuffSerializeUtil implements SeriallizeUtil {
    /**
     * 避免每次序列化都重新申请Buffer空间
     */
    private static LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

    /*
     * 缓存对象及对象schema信息集合
     */
    private static final Map<Class<?>, Schema<?>> CACHE_SCHEMA = Maps.newConcurrentMap();

    /**
     * 序列化/反序列化包装类 Class 对象
     */
    private static final Class<SerializeDeserializeWrapper> WRAPPER_CLASS = SerializeDeserializeWrapper.class;

    /**
     * 序列化/反序列化包装类 Schema 对象
     */
    private static final Schema<SerializeDeserializeWrapper> WRAPPER_SCHEMA = RuntimeSchema.createFrom(WRAPPER_CLASS);

    /*
     * 需要使用包装类进行序列化/反序列化的class集合
     */
    private static final Set<Class<?>> WRAPPER_SET = new HashSet<>();

    /**
     * 预定义一些protoStuff无法直接序列化的类
     */
    static {
        WRAPPER_SET.add(List.class);
        WRAPPER_SET.add(ArrayList.class);
        WRAPPER_SET.add(CopyOnWriteArrayList.class);
        WRAPPER_SET.add(LinkedList.class);

        WRAPPER_SET.add(Map.class);
        WRAPPER_SET.add(HashMap.class);
        WRAPPER_SET.add(TreeMap.class);
        WRAPPER_SET.add(Hashtable.class);
        WRAPPER_SET.add(SortedMap.class);
        WRAPPER_SET.add(Map.class);
    }

    public static void registerWrapperClass(Class clazz) {
        WRAPPER_SET.add(clazz);
    }

    @Override
    public <T> T byteToObject(byte[] data, Class<T> clazz) {
        if (null == data) return null;
        return byteToObject(data, clazz, true);
    }

    public <T> T byteToObject(byte[] data, Class<T> clazz, Boolean check) {
        T obj = null;
        try {
            if (!WRAPPER_SET.contains(clazz)) {
                T message = clazz.newInstance();
                Schema<T> schema = getSchema(clazz);
                ProtostuffIOUtil.mergeFrom(data, message, schema);
                obj=  message;
                // 是否需要校验对象为空
                if (check && !ProtoStuffSerializeUtil.check(obj)) {
                    obj = null;
                }
            } else {
                SerializeDeserializeWrapper<T> wrapper = new SerializeDeserializeWrapper<>();
                ProtostuffIOUtil.mergeFrom(data, wrapper, WRAPPER_SCHEMA);
                obj = wrapper.getData();
            }
        } catch (Exception e) {
            log.error("反序列化对象异常:", clazz.getName(), e);
            throw new IllegalStateException(e.getMessage(), e);
        }

        return obj;
    }

    @Override
    public <T> byte[] objectToByte(T obj) {
        if (null == obj) return null;
        Class<T> clazz = (Class<T>) obj.getClass();
        try {
            Object serializeObject = obj;
            Schema schema = WRAPPER_SCHEMA;
            if (!WRAPPER_SET.contains(clazz)) {
                schema = getSchema(clazz);
            } else {
                serializeObject = SerializeDeserializeWrapper.builder(obj);
            }
            return ProtostuffIOUtil.toByteArray(serializeObject, schema, buffer);
        }
        catch (Exception e) {
            log.error("序列化对象异常:", obj.toString(), e);
            throw new IllegalStateException(e.getMessage(), e);
        }
        finally {
            buffer.clear();
        }
    }

    private static <T> Schema<T> getSchema(Class<T> clazz) {
        Schema<T> schema = (Schema<T>) CACHE_SCHEMA.get(clazz);
        if (Objects.isNull(schema)) {
            schema = RuntimeSchema.getSchema(clazz);
            if (Objects.nonNull(schema)) {
                CACHE_SCHEMA.put(clazz, schema);
            }
        }
        return schema;
    }

    /**
     * 判断对象是否为空
     * @param obj
     * @return
     */
    private static boolean check(Object obj) {
        try {
            for (Field field : obj.getClass().getDeclaredFields()) {
                //把私有属性公有化
                field.setAccessible(true);
                Object object = field.get(obj);
                // 只要除了serialVersionUID外的某一个属性非空，则说明读取正常
                if (!"serialVersionUID".equals(field.getName()) && !Objects.isNull(object)) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("allFieldIsNull异常:", e.getMessage());
            return false;
        }
        return false;
    }
}
