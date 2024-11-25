package com.neil.network.protocol.ldap;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.net.SocketFactory;

/*
 * @Classname CustomSocketFactory
 * @Version information V1.0
 * @Date 2024/6/24
 * @Copyright notice iWhaleCloud
 * @userName NEIL
 */
public class CustomSocketFactory extends SocketFactory {
    public static SocketFactory getDefault() {
        synchronized (CustomSocketFactory.class) {
            return new CustomSocketFactory();
        }

    }

    @Override
    public Socket createSocket() throws SocketException {
        Socket socket = new Socket();
        socket.setSoLinger(true, 0);
        return socket;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        Socket socket = new Socket(host, port);
        socket.setSoLinger(true, 0);
        return socket;

    }

    @Override
    public Socket createSocket(InetAddress address, int port) throws IOException {
        Socket socket = new Socket(address, port);
        socket.setSoLinger(true, 0);
        return socket;
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localAddr, int localPort) throws IOException, UnknownHostException {
        Socket socket = new Socket(host, port, localAddr, localPort);
        socket.setSoLinger(true, 0);
        return socket;
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddr, int localPort) throws IOException {
        Socket socket = new Socket(address, port, localAddr, localPort);
        socket.setSoLinger(true, 0);
        return socket;
    }
}