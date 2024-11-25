package com.neil.network.protocol.telnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/8/2 9:55
 * @Version 1.0
 */
public class BrokenpipeServer {
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server: Waiting for a client to connect...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Server: Client connected.");

            // Read data from the client
            InputStream inputStream = clientSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            if (bytesRead > 0) {
                String dataReceived = new String(buffer, 0, bytesRead);
                System.out.println("Server: Received data from client: " + dataReceived);
            }

            System.out.println("Server: Waiting for client to close the connection...");
            clientSocket.close();
            System.out.println("Server: Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void server() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream socketin = null;
        OutputStream socketOut = null;
        try {
            serverSocket = new ServerSocket(8100);
            socket = serverSocket.accept(); //等待客户连接
            //runTask(socket);
            socketin = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(socketin));
            socketOut = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(socketOut, true);
            String msg = null;
            // 接收消息并回复
            /*while ((msg = br.readLine()) != null) {
                System.out.println("recv:" + msg);
                pw.println("I am Server,and had been recived:" + msg);
            }*/
            // 模拟一直写消息
            int count = 0;
            while (true) {
                pw.println(count);
                count++;
                if (count >= 50000) {
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (socket != null) {
                    socket.close(); //断开连接
                    socketin.close();
                    socketOut.close();
                    socket = null;
                    socketin = null;
                    socketOut = null;
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void runTask(Socket serverSocket) {
        System.out.println("start task");
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2, 2, TimeUnit.SECONDS);
    }
}
