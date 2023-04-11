package com.neil.netty;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/19 21:33
 * @Version 1.0
 */
public class NettyTest {

    public static void main(String[] args) {
        NettyRemotingClient client = new NettyRemotingClient();
        client.start(8081);
        client.sendMsg("Hello!Netty!");
    }

}
