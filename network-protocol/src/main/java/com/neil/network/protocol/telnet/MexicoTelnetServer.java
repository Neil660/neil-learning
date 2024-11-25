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
 * @Decription
 * @Author NEIL
 * @Date 2023/10/24 17:49
 * @Version 1.0
 */
public class MexicoTelnetServer {
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

                // 直接登录成功，不要发登录指令

                while ((msg = br.readLine()) != null) {
                    System.out.println("recv:" + msg);
                    if (msg.equalsIgnoreCase("exit;")) { //结束通信
                        System.out.println("connection reset");
                        break;
                    }
                    else {
                        // 1、超时回复，出现java.net.SocketTimeoutException: Read timed out
                        // 超时时间第一次默认10秒，后面就跟setSoTimeout设置的一致；最大不能超过10秒
                        // 如果设置了telnetClient.setSoTimeout(timeoutMills)且值小于60秒，那么客户端就会报SocketTimeoutException异常
                        //Thread.sleep(60 * 1000);
                        //System.out.println("finish sleep 60s and response.");
                        pw.println("response:" + msg);


                        // 2、不发结束符，出现java.net.SocketTimeoutException: Read timed out
                        // 超时时间第一次默认10秒，后面就跟setSoTimeout设置的一致；最大不能超过10秒
                        /*pw.print("response");*/

                        // 3、回复持续时间过长，出现java.net.SocketTimeoutException: Read timed out
                        // 超时时间第一次默认10秒，后面就跟setSoTimeout设置的一致；最大不能超过10秒
                        /*for (int i = 0; i < 60; i++) {
                            pw.print(i);
                            Thread.sleep(1000);
                        }
                        pw.println("\n");*/

                        // 4、在第三种情况下，如果在持续回复时间内，客户端再发来消息，也会超时，但是超时时间跟setSoTimeout一致，可以超过10秒
                        // 这意味着，如果只有一个服务端线程的情况下，当前线程还在处理上一个客户端发来的请求，此时客户端又发来一个请求，如果不能在soTimeout时间内响应，就会出现超时异常
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
