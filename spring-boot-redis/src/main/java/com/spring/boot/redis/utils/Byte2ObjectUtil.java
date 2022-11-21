package com.spring.boot.redis.utils;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/11/20 17:08
 * @Version 1.0
 */
@Slf4j
public class Byte2ObjectUtil {
    @SuppressFBWarnings(value = "OBJECT_DESERIALIZATION", justification = "ignore this warning")
    public static Object byteToObject(byte[] bytes) {
        Object object;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            object = ois.readObject();
            return object;
        }
        catch (Exception e) {
            log.error("5040910", e.getMessage(), e);
        }
        finally {
            if (ois != null) {
                try {
                    ois.close();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("", e.getMessage(), e);
                }
            }
            if (bais != null) {
                try {
                    bais.close();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("", e.getMessage(), e);
                }
            }
        }
        return null;
    }

    public static byte[] objectToByte(Object obj) {

        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            return bos.toByteArray();
        }
        catch (IOException e) {
            log.error("5040910", e.getMessage(), e);
        }
        finally {
            if (oos != null) {
                try {
                    oos.close();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("", e.getMessage(), e);
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("", e.getMessage(), e);
                }
            }
        }
        return null;
    }

    @SuppressFBWarnings(
            value = "OBJECT_DESERIALIZATION", justification = "ignore this warning")
    public static <T> T byteToObject(byte[] bytes, Class<T> clz) {
        Object object;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            object = ois.readObject();
            return get(clz, object);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (ois != null) {
                try {
                    ois.close();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (bais != null) {
                try {
                    bais.close();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Object转成指定的类型
     * @param clz
     * @param o
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T get(Class<T> clz, Object o)
            throws IllegalAccessException, InstantiationException {
        if (clz.isInstance(o)) {
            //T newObject = clz.newInstance();
            //BeanUtils.copyProperties(clz.cast(o), newObject);
            return clz.cast(o);
        }
        return null;
    }

    public static Object castStringToX(Class<?> clazz, String str) {
        Object value = null;
        if (clazz.getName().equals("java.lang.Integer") || clazz.getName().equals("int")) {
            value = Integer.parseInt(str);
        }
        else if (clazz.getName().equals("java.lang.Boolean") || clazz.getName().equals("boolean")) {
            value = Boolean.parseBoolean(str);
        }
        else if (clazz.getName().equals("java.lang.Long") || clazz.getName().equals("long")) {
            value = Long.parseLong(str);
        }
        else {
            value = str;
        }
        return value;
    }

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
            e.printStackTrace();
        }
        finally {

        }
        return null;
    }

    /**
     * @param bytes
     * @return
     */
    public static byte[] uzip(byte[] bytes) {
        CompressorInputStream cis = null;
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            cis = new GzipCompressorInputStream(bais);
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int size = 0;
            while ((size = cis.read(buffer)) > 0) {
                baos.write(buffer, 0, size);
            }
            byte[] rs = baos.toByteArray();
            return rs;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (baos != null) {
                try {
                    baos.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (cis != null) {
                try {
                    cis.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bais != null) {
                try {
                    bais.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
