package com.neil.multi.thread.example.case2;

import com.neil.multi.thread.utils.Tools;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Decription 下载线程
 * @Author NEIL
 * @Date 2022/12/29 17:25
 * @Version 1.0
 */
public class DownloadTask implements Runnable {
    private final long lowerBound;
    private final long upperBound;
    private final DownloadBuffer xbuf;
    private final URL requestURL;
    private final AtomicBoolean cancelFlag;

    public DownloadTask(long lowerBound, long upperBound, URL requestURL,
                        Storage storage, AtomicBoolean cancelFlag) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.requestURL = requestURL;
        this.xbuf = new DownloadBuffer(lowerBound, upperBound, storage);
        this.cancelFlag = cancelFlag;
    }

    /**
     * 对指定的URL发起HTTP分段下载请求
     * @param requestURL 请求地址
     * @param lowerBound 下载起始位置
     * @param upperBound 下载末尾位置
     * @return
     * @throws IOException
     */
    private static InputStream issueRequest(URL requestURL, long lowerBound,
                                            long upperBound) throws IOException {
        Thread me = Thread.currentThread();
        System.out.println(me + "->[" + lowerBound + "," + upperBound + "]");
        final HttpURLConnection conn;
        InputStream in = null;
        conn = (HttpURLConnection) requestURL.openConnection();
        String strConnTimeout = System.getProperty("x.dt.conn.timeout");
        int connTimeout = null == strConnTimeout ? 60000 : Integer
                .valueOf(strConnTimeout);
        conn.setConnectTimeout(connTimeout);

        String strReadTimeout = System.getProperty("x.dt.read.timeout");
        int readTimeout = null == strReadTimeout ? 60000 : Integer
                .valueOf(strReadTimeout);
        conn.setReadTimeout(readTimeout);

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Connection", "Keep-alive");
        // Range: bytes=0-1024
        // 下载lowerBound到upperBound这部分的字节
        conn.setRequestProperty("Range", "bytes=" + lowerBound + "-" + upperBound);
        conn.setDoInput(true);
        conn.connect();

        int statusCode = conn.getResponseCode();
        if (HttpURLConnection.HTTP_PARTIAL != statusCode) {
            conn.disconnect();
            throw new IOException("Server exception,status code:" + statusCode);
        }

        System.out.println(me + "-Content-Range:" + conn.getHeaderField("Content-Range")
                + ",connection:" + conn.getHeaderField("connection"));

        in = new BufferedInputStream(conn.getInputStream()) {
            @Override
            public void close() throws IOException {
                try {
                    super.close();
                }
                finally {
                    conn.disconnect();
                }
            }
        };

        return in;
    }

    @Override
    public void run() {
        if (cancelFlag.get()) {
            return;
        }
        // 可读取字节的通道
        ReadableByteChannel channel = null;
        try {
            channel = Channels.newChannel(issueRequest(requestURL, lowerBound,
                    upperBound));
            // 分配一个新的字节缓冲区。
            ByteBuffer buf = ByteBuffer.allocate(1024);
            while (!cancelFlag.get() && channel.read(buf) > 0) {
                // 将从网络读取的数据写入缓冲区
                xbuf.write(buf);
                buf.clear();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            Tools.silentClose(channel, xbuf);
        }
    }
}
