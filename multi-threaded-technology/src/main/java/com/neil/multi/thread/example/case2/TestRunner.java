package com.neil.multi.thread.example.case2;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/12/29 17:33
 * @Version 1.0
 */
public class TestRunner {
    public static void main(String[] args) throws Exception {
        BigFileDownloader downloader = null;

        downloader = new BigFileDownloader("https://softforspeed.51xiazai.cn/down/2022down/9/16/ideaIU2022.2.2.exe");

        // 平均每个处理器执行8个下载子任务
        int N_CPU = Runtime.getRuntime().availableProcessors();
        int workerThreadsCount = N_CPU * 8;
        long reportInterval = 1;
        downloader.download(workerThreadsCount, reportInterval * 500, System.currentTimeMillis());
    }
}
