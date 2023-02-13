package com.multi.thread.example.case3;

import com.multi.thread.utils.Tools;

/**
 * @Decription 告警功能模块
 * @Author NEIL
 * @Date 2023/1/3 15:23
 * @Version 1.0
 */
public class TestRunner {
    final static AlarmAgent alarmAgent;

    static {
        alarmAgent = AlarmAgent.getInstance();
        alarmAgent.init();
    }

    public static void main(String[] args) throws InterruptedException {
        // 告警发送线程，也是等待线程
        alarmAgent.sendAlarm("Database offline!");
        Tools.randomPause(12000);
        alarmAgent.sendAlarm("XXX service unreachable!");
    }
}
