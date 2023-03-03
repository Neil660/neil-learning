package com.neil.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/2 18:11
 * @Version 1.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({CacheProperties.class})
public class NeilCommonAutoConfiguration {

    /*@Bean(name = "cacheManager")
    @ConditionalOnProperty(prefix = "neil.cache", value = {"enabled"}, matchIfMissing = true, havingValue = "true")
    public SimpleCacheManager getSimpleCacheManager(CacheProperties cacheProperties) {
        Set<ConcurrentMapCache> list = new HashSet<>();
        cacheProperties.getMap().forEach((k, v) -> {
            ConcurrentMapCache concurrentMapCache = new ConcurrentMapCache(v);
            list.add(concurrentMapCache);
        });

        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(list);
        return simpleCacheManager;
    }*/
}
