package com.neil.network.protocol.telnet;

import com.google.common.base.Charsets;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/8/2 9:55
 * @Version 1.0
 */
@Slf4j
public class BrokenpipeClient {

    public void client2() {
        StringBuilder sb = new StringBuilder();
        byte[] buff = new byte[2048];
        Socket socket = null;
        InputStream in;
        OutputStream out;
        try {
            socket = new Socket("localhost", 8100);
            socket.setKeepAlive(true);
            in = socket.getInputStream();
            out = socket.getOutputStream();
            int length;
            if (socket != null) {
                log.info("build a connetion");
                while (true) {
                    length = in.read(buff);
                    if (length == -1) {
                        log.info("connection reset");
                        break;
                    }
                    sb.append(new String(buff, 0, length, Charsets.UTF_8));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            log.info(sb.toString().split("\n")[sb.toString().split("\n").length - 1]);
            try {
                socket.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            socket = null;
            in = null;
            out = null;
        }
    }

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345)) {
            System.out.println("Client: Connected to server.");

            // Simulate the error by closing the socket before writing data
            socket.close();
            System.out.println("Client: Connection closed.");

            // Try to write to the server after the connection is closed
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("Hello, server!".getBytes());
            System.out.println("Client: Data sent to server.");
        } catch (IOException e) {
            System.err.println("Client: Error occurred - " + e.getMessage());
        }
    }

    public void client() {
        Socket socket = null;
        InputStream in;
        OutputStream out;
        try {
            socket = new Socket("localhost", 8100);
            socket.setKeepAlive(true);
            in = socket.getInputStream();
            out = socket.getOutputStream();
            if (socket != null) {
                Thread.sleep(4000);
                //System.out.println("begin to send logout msg ...");
                //String msg1 = "exit;" + "\r\n";
                String msg1 = "123";
                while (true) {
                    System.out.println("Sending msg:" + msg1);
                    out.write(msg1.toString().getBytes(Charsets.UTF_8));
                    out.flush();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            socket = null;
            in = null;
            out = null;
        }
    }
}
