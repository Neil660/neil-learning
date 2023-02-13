package com.multi.thread.example.case8;

import com.multi.thread.utils.Tools;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @Decription 使用PipedOutputStream和PipedInputStream实现的从网络上边下载边解析的RSS（Rich Site Summary）阅读器
 * @Author NEIL
 * @Date 2023/1/30 17:56
 * @Version 1.0
 */
public class ConcurrentRSSReader {

    /**
     * 创建rss-loader线程
     * @param url
     * @return
     * @throws IOException
     */
    public static InputStream loadRSS(final String url) throws IOException {
        final PipedInputStream in = new PipedInputStream();
        // 以in为参数创建PipedOutputStream实例
        final PipedOutputStream pos = new PipedOutputStream(in);

        Thread workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    doDownload(url, pos);
                }
                catch (Exception e) {
                    // RSS数据下载过程中出现异常时，关闭相关输出流和输入流。
                    // 注意，此处我们不能像平常那样在finally块中关闭相关输出流
                    Tools.silentClose(pos, in);
                    e.printStackTrace();
                }
            }
        }, "rss-loader");

        workerThread.start();
        return in;
    }

    /**
     * 发起HTTP请求 -> 输入流InputStream <-> 可read管道 -> byteBuffer缓冲流 -> 可write管道 <-> 输出流OutputStream
     * @param url
     * @param os
     * @throws Exception
     */
    static void doDownload(String url, OutputStream os) throws Exception {
        ReadableByteChannel readChannel = null;
        WritableByteChannel writeChannel = null;
        try {
            ByteBuffer buf = ByteBuffer.allocate(1024);

            // 对指定的URL发起HTTP请求，返回输入流
            BufferedInputStream in = issueRequest(url);
            // 创建read管道
            readChannel = Channels.newChannel(in);
            // 创建write管道
            writeChannel = Channels.newChannel(os);
            // 将数据从channel管道写到字节缓冲流中
            while (readChannel.read(buf) > 0) {
                buf.flip();
                // 将字节缓冲流中的数据写道write管道
                writeChannel.write(buf);
                buf.clear();
            }
        }
        finally {
            Tools.silentClose(readChannel, writeChannel);
        }
    }

    /**
     * 返回请求的输入流
     * @param url
     * @return
     * @throws Exception
     */
    static BufferedInputStream issueRequest(String url) throws Exception {
        URL requestURL = new URL(url);
        final HttpURLConnection conn = (HttpURLConnection) requestURL
                .openConnection();
        conn.setConnectTimeout(2000);
        conn.setReadTimeout(2000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Connection", "close");
        conn.setDoInput(true);
        conn.connect();
        int statusCode = conn.getResponseCode();
        if (HttpURLConnection.HTTP_OK != statusCode) {
            conn.disconnect();
            throw new Exception("Server exception,status code:" + statusCode);
        }

        BufferedInputStream in = new BufferedInputStream(conn.getInputStream()) {
            // 覆盖BufferedInputStream的close方法，使得输入流被关闭的时候HTTP连接也随之被关闭
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
}
