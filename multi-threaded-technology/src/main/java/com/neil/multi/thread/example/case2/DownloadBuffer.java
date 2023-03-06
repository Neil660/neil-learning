package com.neil.multi.thread.example.case2;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @Decription 缓冲器，避免多次写入文件，减少IO
 * @Author NEIL
 * @Date 2022/12/29 17:31
 * @Version 1.0
 */
public class DownloadBuffer implements Closeable {
    /**
     * 当前Buffer中缓冲的数据相对于整个存储文件的位置偏移
     */
    private long globalOffset;
    private long upperBound;
    private int offset = 0;
    public final ByteBuffer byteBuf;
    private final Storage storage;

    public DownloadBuffer(long globalOffset, long upperBound,
                          final Storage storage) {
        this.globalOffset = globalOffset;
        this.upperBound = upperBound;
        this.byteBuf = ByteBuffer.allocate(1024 * 1024);
        this.storage = storage;
    }

    /**
     * 采用双缓冲区的方式，将buf传递过来的字节存储到byteBuf中，等到byteBuf存储不下时就整个写入文件
     * @param buf
     * @throws IOException
     */
    public void write(ByteBuffer buf) throws IOException {
        // ByteBuffer是一个字节属组，每一次写入数据或者获取数据，position都会移动一位。相关属性：
        //     capacity在读写模式下都是固定的，就是我们分配的缓冲大小，一个固定的大小
        //     position类似于读写指针，表示当前读(写)到什么位置
        //     limit表示最多可以从缓冲区中读取limit个字节，相当于实际存储数据的大小size()
        int length = buf.position();
        final int capacity = byteBuf.capacity();
        // 当前缓冲区已满，或者剩余容量不够容纳新数据
        if (offset + length > capacity || length == capacity) {
            // 将缓冲区中的数据写入文件
            flush();
        }
        byteBuf.position(offset);
        // limit = position;position = 0;
        buf.flip();
        // 将buf缓冲区中剩余的字节都传输到该缓存区中
        byteBuf.put(buf);
        offset += length;
    }

    public void flush() throws IOException {
        int length;
        byteBuf.flip();
        length = storage.store(globalOffset, byteBuf);
        byteBuf.clear();
        globalOffset += length;
        offset = 0;
    }

    @Override
    public void close() throws IOException {
        System.out.println("globalOffset:" + globalOffset + ",upperBound:" + upperBound);
        if (globalOffset < upperBound) {
            flush();
        }
    }
}
