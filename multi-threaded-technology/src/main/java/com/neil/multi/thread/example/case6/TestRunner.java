package com.neil.multi.thread.example.case6;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/1/30 14:26
 * @Version 1.0
 */
public class TestRunner {
    public static void main(String[] args) {
        ShootPractice sp = new ShootPractice(4, 5, 24);
        try {
            sp.start();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
