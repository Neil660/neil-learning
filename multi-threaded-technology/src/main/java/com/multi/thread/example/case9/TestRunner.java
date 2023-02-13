package com.multi.thread.example.case9;

import com.multi.thread.utils.Debug;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Exchanger;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/1/31 11:00
 * @Version 1.0
 */
public class TestRunner {
    static TerminatableTaskRunner taskRunner = new TerminatableTaskRunner();

    static {
        taskRunner.init();
    }

    private static Thread createTask() {
        // 最多执行10秒的线程
        Thread task = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("thread is starting...i=" + i);
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return task;
    }

    public static void main(String[] args) {
        // 最多执行10秒的线程
        Thread task = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("thread is starting...i=" + i);
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        try {
            taskRunner.submit(task);
            // 在指定时间后取消提交给taskRunner的任务task
            Timer timer = new Timer(true);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    taskRunner.cancelTask();
                }
            }, 2000);

            Thread.sleep(2000);
            // 执行其他任务
            taskRunner.submit(new Runnable() {
                @Override
                public void run() {
                    Debug.info("Some other task");
                }
            });

            taskRunner.shutdown();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
