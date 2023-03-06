package com.neil.redis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/2/28 17:10
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties(value = {ClusterConfigurationProperties.class})
@ConditionalOnProperty(name = "spring.redis.type", havingValue = "cluster")
public class RedisClusterConfig {

    @Bean
    public RedisConnectionFactory connectionFactory(ClusterConfigurationProperties clusterConfigurationProperties) {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterConfigurationProperties.getNodes());
        redisClusterConfiguration.setMaxRedirects(clusterConfigurationProperties.getMaxRedirects());
        redisClusterConfiguration.setPassword(clusterConfigurationProperties.getPassword());
        return new LettuceConnectionFactory(redisClusterConfiguration);
    }

    @Bean
    public RedisTemplate<String, byte[]> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }



}
