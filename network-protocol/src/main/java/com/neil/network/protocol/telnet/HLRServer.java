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
 * @Date 2023/10/7 19:00
 * @Version 1.0
 */
public class HLRServer {
    /**
      * 登录、发指令成功返回：<
      * conn: RESP_CODE_REGEXP=<||RESP_FAULTCODE_REGEXP=FAULT CODE (.*?)||RESP_FAULTDETAIL_REGEXP=Error Text = (.*?)||IN_LOGIN=USERCODE:||IN_PASSWORD=PASSWORD:||IN_DOMAIN=DOMAIN:||DOMAIN=TelcelOneCore@321||LOGIN_END=<
      */
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8010); // 不会阻塞
        while (true) {
            Socket socket = null;
            try {

                // 改IP可测试网络连接层面的connection timeout

                socket = serverSocket.accept(); //等待客户连接
                System.out.println("a socket had built...");
                InputStream socketin = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(socketin));
                OutputStream socketOut = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(socketOut, true);
                String msg = null;

                // 模拟登录成功
                pw.println("<");

                while ((msg = br.readLine()) != null) {
                    System.out.println("收到的消息:" + msg);

                    if (msg.equals("\n") || msg.equals("\r") || msg.equals("\r\n")) { // 回车
                        pw.println("\u0003<");
                    }
                    if (msg.contains("exit;")) { //结束通信
                        break;
                    }
                    else {
                        // 可测试read timeout
//                        Thread.sleep(60 * 1000);
//                        System.out.println("finish sleep 60s and response.");

                        pw.println("<");
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
