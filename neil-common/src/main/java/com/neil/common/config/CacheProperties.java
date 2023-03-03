package com.neil.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Decription 缓存配置类
 * @Author NEIL
 * @Date 2023/3/2 18:09
 * @Version 1.0
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "neil.cache")
public class CacheProperties {
    private Map<String, String> map = new HashMap<>();

}
