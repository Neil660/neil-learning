package com.neil.network.protocol.telnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Decription 应用不能开debug模式，上传该文件，执行javac MinsatCT01Server.java编译，执行java MinsatCT01Server启动
 * @Author NEIL
 * @Date 2023/7/12 11:15
 * @Version 1.0
 */
public class MINSATServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(17001);
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept(); //等待客户连接
                InputStream socketin = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(socketin));
                OutputStream socketOut = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(socketOut, true);
                String msg = null;
                while ((msg = br.readLine()) != null) {
                    System.out.println("收到的消息:" + msg);
                    if (msg.contains("LOGOUT")) { //结束通信
                        break;
                    }
                    else if (msg.contains("LOGIN")) {
                        pw.println("RESP:0");
                    }
                    else {
                        // 可测试read timeout
//                        Thread.sleep(60 * 1000);
//                        System.out.println("finish sleep 60s and response.");

                        pw.println("RESP:0:;");
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (socket != null) socket.close(); //断开连接
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
