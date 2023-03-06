package com.neil.multi.thread.example.case0;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @Decription ByteBuffer字节缓冲区的基础使用
 * @Author NEIL
 * @Date 2023/1/29 15:02
 * @Version 1.0
 */
public class TestRunner5 {
    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(100);
        int capacity = buf.capacity(); // 初始化的总容量
        int position = buf.position();
        int limit = buf.limit();
        System.out.println("init...");
        System.out.println("capacity="+ buf.capacity() + ",position=" + buf.position() + ",limit=" + buf.limit());

        buf.put((byte) 'a');
        buf.put((byte) 'b');
        buf.put((byte) 'c');
        System.out.println("after put three elements...");
        System.out.println("capacity="+ buf.capacity() + ",position=" + buf.position() + ",limit=" + buf.limit());

        buf.flip();
        System.out.println("after flip...");
        System.out.println("capacity="+ buf.capacity() + ",position=" + buf.position() + ",limit=" + buf.limit());
    }
}
