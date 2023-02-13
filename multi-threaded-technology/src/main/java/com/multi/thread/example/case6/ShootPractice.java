package com.multi.thread.example.case6;

import com.multi.thread.utils.Debug;
import com.multi.thread.utils.Tools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Decription 士兵打靶训练——CyclicBarrier的应用
 *     所有参与训练的士兵（Soldier）被分为若干组（Rank），其中每组被称为一排。一排中士兵的个数等于靶子的个数。每次只能够有一排
 *     士兵进行射击。一排中的士兵必须同时开始射击，并且射击完毕的士兵必须等待同排的其他所有士兵射击完毕后才能够整排地撤离射击点
 *     。一排中的士兵射击结束后腾出射击点和靶子，换另外一排中的士兵进行下一轮射击，如此交替进行，直到训练时间结束。
 * @Author NEIL
 * @Date 2023/1/30 14:29
 * @Version 1.0
 */
public class ShootPractice {
    // 参与打靶训练的全部士兵
    final Soldier[][] rank;
    // 靶的个数，即每排中士兵的个数
    final int N;
    // 打靶持续时间（单位：秒）
    final int lasting;
    // 标识是否继续打靶
    volatile boolean done = false;
    // 用来指示进行下一轮打靶的是哪一排的士兵
    volatile int nextLine = 0;
    final CyclicBarrier shiftBarrier;
    final CyclicBarrier startBarrier;

    /**
     * 初始化参数
     * @param N
     * @param lineCount
     * @param lasting
     */
    public ShootPractice(int N, final int lineCount, int lasting) {
        this.N = N;
        this.lasting = lasting;
        this.rank = new Soldier[lineCount][N];
        for (int i = 0; i < lineCount; i++) {
            for (int j = 0; j < N; j++) {
                rank[i][j] = new Soldier(i, j);
            }
        }

        startBarrier = new CyclicBarrier(N);
        shiftBarrier = new CyclicBarrier(N, new Runnable() {
            @Override
            public void run() {
                nextLine = (nextLine + 1) % lineCount;
                Debug.info("Next turn is :%d", nextLine);
            }
        });
    }

    /**
     * 启动工作者线程，在全部射击结束后停止线程
     * @throws InterruptedException
     */
    public void start() throws InterruptedException {
        Thread[] allSThread = new Thread[N];
        for (int i = 0; i < N; i++) {
            allSThread[i] = new Shooting(i);
            allSThread[i].start();
        }

        // 指定时间后停止打靶，此时可能已经开始第二轮了
        Thread.sleep(lasting * 1000);
        stop();
        for (Thread thread : allSThread) {
            thread.join();
        }
        Debug.info("Practice finished.");
    }

    /**
     * 设置线程停止标志
     */
    public void stop() {
        done = true;
    }

    class Shooting extends Thread {
        final int index;

        public Shooting(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            try {
                // 还有下一排就要继续循环
                while (!done) {
                    // 等待当前排其他人，准备一起射击
                    startBarrier.await();
                    // 开始射击
                    rank[nextLine][index].fire();
                    // 等待别的士兵射击都完成，然后一起换下一排
                    shiftBarrier.await();
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    // 参与打靶训练的士兵
    static class Soldier {
        private final int row;
        private final int seqNo;

        public Soldier(int row, int seqNo) {
            this.seqNo = seqNo;
            this.row = row;
        }

        public void fire() {
            Debug.info(this + " start firing...");
            Tools.randomPause(5000);
            System.out.println(this + " fired.");
        }

        @Override
        public String toString() {
            return "Soldier:" + row + "-" + seqNo;
        }
    }
}
