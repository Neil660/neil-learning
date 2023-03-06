package com.neil.multi.thread.example.case2;

import com.neil.multi.thread.utils.Tools;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Decription 写入本地文件
 * @Author NEIL
 * @Date 2022/12/29 17:23
 * @Version 1.0
 */
public class Storage implements Closeable, AutoCloseable {
    // 可自由访问文件任意位置
    private final RandomAccessFile randomAccessFile;
    // 文件通道，提供写入数据的通道 【NIO逐行读取法】
    private final FileChannel fileChannel;
    // 写入数据的总长度
    protected final AtomicLong totalWrites = new AtomicLong(0);

    public Storage(long fileSize, String fileShortName) throws IOException {
        String fullFileName = System.getProperty("java.io.tmpdir") + "/"
                + fileShortName;
        String localFileName;
        // 创建用来存储的文件
        localFileName = createStoreFile(fileSize, fullFileName);
        randomAccessFile = new RandomAccessFile(localFileName, "rw");
        fileChannel = randomAccessFile.getChannel();
    }

    /**
     * 创建文件并返回名称
     * @param fileSize
     * @param fullFileName
     * @return
     * @throws IOException
     */
    private String createStoreFile(final long fileSize, String fullFileName)
            throws IOException {
        File file = new File(fullFileName);
        System.out.println("create local file:" + fullFileName);
        RandomAccessFile raf;
        // “r” 以只读方式来打开指定文件夹。如果试图对该RandomAccessFile执行写入方法，都将抛出IOException异常。
        // “rw” 以读，写方式打开指定文件。如果该文件尚不存在，则试图创建该文件。
        // “rws” 以读，写方式打开指定文件。相对于”rw” 模式，还要求对文件内容或元数据的每个更新都同步写入到底层设备。
        // “rwd” 以读，写方式打开指定文件。相对于”rw” 模式，还要求对文件内容每个更新都同步写入到底层设备。
        raf = new RandomAccessFile(file, "rw");
        try {
            raf.setLength(fileSize);
        }
        finally {
            Tools.silentClose(raf);
        }
        return fullFileName;
    }

    /**
     * 将data中指定的数据写入文件
     * @param offset  写入数据在整个文件中的起始偏移位置（多线程环境下的记录指针）
     * @param byteBuf byteBuf必须在该方法调用前执行byteBuf.flip()
     * @return 写入文件的数据长度
     * @throws IOException
     */
    public int store(long offset, ByteBuffer byteBuf)
            throws IOException {
        int length;
        fileChannel.write(byteBuf, offset);
        length = byteBuf.limit();
        totalWrites.addAndGet(length);
        return length;
    }

    public long getTotalWrites() {
        return totalWrites.get();
    }

    @Override
    public synchronized void close() throws IOException {
        if (fileChannel.isOpen()) {
            Tools.silentClose(fileChannel, randomAccessFile);
        }
    }
}
