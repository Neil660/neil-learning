package com.neil;

import com.neil.netty.NettyRemotingClient;
import com.neil.netty.NettyRemotingServer;
import com.neil.netty.time.TimeServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/8 18:10
 * @Version 1.0
 */
@SpringBootApplication
public class SpringBootNettyApplication implements CommandLineRunner {
    @Value("${discard-server.port:8080}")
    private String port;

    @Autowired
    private TimeServer timeServer;
    @Autowired
    private NettyRemotingServer server;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootNettyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // time服务
        //timeServer.start(Integer.parseInt(port));
        // 普通服务
        int port = 8081;
        server.start(port);
    }
}
