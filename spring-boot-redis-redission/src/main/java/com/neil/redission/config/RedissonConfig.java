package com.neil.redission.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/1 10:51
 * @Version 1.0
 */
@Configuration
public class RedissonConfig {
    @Value("${spring.redission.host}")
    private String host;

    @Value("${spring.redission.port}")
    private String port;

    @Bean
    public RedissonClient getRedissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+host+":"+port+"");

        return Redisson.create(config);
    }
}
