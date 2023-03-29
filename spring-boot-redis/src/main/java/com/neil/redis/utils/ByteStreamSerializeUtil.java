package com.neil.redis.utils;

import com.neil.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @Decription 压缩效率不如Protostuff
 * @Author NEIL
 * @Date 2023/3/1 14:25
 * @Version 1.0
 */
@Slf4j
@Component
public class ByteStreamSerializeUtil implements SeriallizeUtil {

    @Override
    public <T> T byteToObject(byte[] bytes, Class<T> clazz) {
        byte[] zipBytes = uzip(bytes);
        T object;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(zipBytes);
            ois = new ObjectInputStream(bais);
            object = (T) ois.readObject();
            return object;
        }
        catch (Exception e) {
            log.error("5040910", e.getMessage(), e);
        }
        finally {
            Tools.silentClose(ois, bais);
        }
        return null;
    }

    @Override
    public <T> byte[] objectToByte(T obj) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            return zip(bos.toByteArray());
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        finally {
            Tools.silentClose(oos, bos);
        }
        return null;
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
            log.error(e.getMessage(), e);
        }
        finally {

        }
        return null;
    }

    /**
     * 解压
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
            Tools.silentClose(baos, cis, bais);
        }
        return null;
    }
}
