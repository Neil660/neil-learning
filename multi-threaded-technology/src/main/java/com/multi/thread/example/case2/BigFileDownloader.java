package com.multi.thread.example.case2;

import com.multi.thread.utils.Tools;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Decription 大文件下载器
 * @Author NEIL
 * @Date 2022/12/29 17:15
 * @Version 1.0
 */
public class BigFileDownloader {
    // 统计耗时
    private Long startTime;
    protected final URL requestURL;
    protected final long fileSize;
    /**
     * 负责已下载数据的存储
     */
    protected final Storage storage;
    final static int N_CPU = Runtime.getRuntime().availableProcessors();
    protected final AtomicBoolean taskCanceled = new AtomicBoolean(false);
    final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, N_CPU * 2, 4,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(N_CPU * 8),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public BigFileDownloader(String strURL) throws Exception {
        requestURL = new URL(strURL);
        // 获取待下载资源的大小（单位：字节）
        fileSize = retieveFileSize(requestURL);
        System.out.println("file total size:" + fileSize);
        // 创建负责存储已下载数据的对象
        storage = new Storage(fileSize, "bigFile");
    }

    /**
     * 下载指定的文件
     * @param taskCount      任务个数
     * @param reportInterval 下载进度报告周期
     * @throws Exception
     */
    public void download(int taskCount, long reportInterval, Long startTime)
            throws Exception {
        this.startTime = startTime;
        long chunkSizePerThread = fileSize / taskCount;
        // 下载数据段的起始字节
        long lowerBound = 0;
        // 下载数据段的结束字节
        long upperBound = 0;

        DownloadTask dt;
        for (int i = taskCount - 1; i >= 0; i--) {
            lowerBound = i * chunkSizePerThread;
            if (i == taskCount - 1) {
                upperBound = fileSize;
            }
            else {
                upperBound = lowerBound + chunkSizePerThread - 1;
            }

            // 1、创建下载任务
            dt = new DownloadTask(lowerBound, upperBound, requestURL, storage,
                    taskCanceled);
            // 2、创建下载线程
            dispatchWork(dt, i);
        }
        // 4、定时报告下载进度
        reportProgress(reportInterval);
        // 5、清理程序占用的资源
        Tools.silentClose(storage);
    }

    protected void dispatchWork(final DownloadTask dt, int workerIndex) {
        // 创建下载线程
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    dt.run();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    // 任何一个下载子任务出现异常就取消整个下载任务
                    executor.shutdownNow();
                    Tools.silentClose(storage);
                }
            }
        });
    }

    /**
     * 根据指定的URL获取相应文件的大小
     * @param requestURL
     * @return
     * @throws Exception
     */
    private static long retieveFileSize(URL requestURL) throws Exception {
        long size = -1;
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) requestURL.openConnection();
            conn.setRequestMethod("HEAD");
            conn.setRequestProperty("Connection", "Keep-alive");
            conn.connect();;

            int statusCode = conn.getResponseCode();
            if (HttpURLConnection.HTTP_OK != statusCode) {
                throw new Exception("Server exception,status code:" + statusCode);
            }
            // 获取内容长度
            String headerField = conn.getHeaderField("Content-Length");
            size = Long.valueOf(headerField);
        }
        finally {
            if (null != conn) {
                conn.disconnect();
            }
        }
        return size;
    }

    /**
     * 报告下载进度
     * @param reportInterval
     * @throws InterruptedException
     */
    private void reportProgress(long reportInterval) throws InterruptedException {
        float lastCompletion;
        int completion = 0;
        while (!taskCanceled.get()) {
            lastCompletion = completion;
            completion = (int) (storage.getTotalWrites() * 100 / fileSize);
            if (completion == 100) {
                break;
            }
            else if (completion - lastCompletion >= 1) {
                System.out.println("Completion:" + completion + "%");
                if (completion >= 90) {
                    reportInterval = 1000;
                }
            }
            Thread.sleep(reportInterval);
        }
        System.out.println("Completion:" + completion + "%");
        if (completion >= 100) {
            System.out.println("Download a big file takes " + (System.currentTimeMillis() - startTime) + "ms");
        }
    }
}
