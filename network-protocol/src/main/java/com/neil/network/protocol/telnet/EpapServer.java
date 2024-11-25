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
 * @Date 2023/8/24 10:51
 * @Version 1.0
 */
public class EpapServer {
    /**
     * 连接指令：connect(iid 9461851, version 1.0, endchar newline, idletimeout 44640, txnmode single)
     * 连接响应指令：rsp (iid 9461851, rc 0, data (connectId 1527915, side active))\n
     * 断开指令：disconnect(iid 9461851)
     * 指令：dlt_sub(iid 9461852,dn 523331670953, timeout 30).
     * 响应指令：rsp (iid 9461854, rc 0, data (dblevel 1626912647))\n
     * conn：RESP_CODE_REGEXP=rc 0,||RESP_FAULTCODE_REGEXP=rc(.*?),||RESP_FAULTDETAIL_REGEXP=data(.*?)`||DROP_TIMEOUT=1
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5873);
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
                    if (msg.contains("disconnect(iid")) { //结束通信
                        System.out.println("connection reset");
                        break;
                    }
                    else if (msg.startsWith("connect")) { //建立连接
                        System.out.println("connect...");

                        // 改IP可测试网络连接层面的connection timeout
                        // 可测试connection timeout
//                        Thread.sleep(3 * 1000);

                        pw.println("rsp (iid 9461851, rc 0, data (connectId 1527915, side active))\n");
                    }
                    else {
                        // 可测试read timeout
//                        Thread.sleep(60 * 1000);
//                        System.out.println("finish sleep 60s and response.");

                        System.out.print("response:");
                        pw.println("rc 0,");
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
