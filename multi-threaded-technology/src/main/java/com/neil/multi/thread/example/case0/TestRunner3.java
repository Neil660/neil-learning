package com.neil.multi.thread.example.case0;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/1/11 20:48
 * @Version 1.0
 */
public class TestRunner3 {
    public static int count= 1;

    static {
        count = 2;
    }

    public TestRunner3() {
        count = 3;
    }

    public static void main(String[] args) {
        TestRunner3 obj = new TestRunner3();
        //System.out.println(obj.count);
        System.out.println(count);
    }
}
