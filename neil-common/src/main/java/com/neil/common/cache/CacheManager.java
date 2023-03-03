package com.neil.common.cache;

import com.neil.common.aware.ServiceLocator;

/**
 * @Decription 全局缓存，作用待定
 * @Author NEIL
 * @Date 2023/3/2 18:13
 * @Version 1.0
 */
public class CacheManager {
    /*private static org.springframework.cache.CacheManager cacheManager = (org.springframework.cache.CacheManager) ServiceLocator
            .lookup("cacheManager");*/
    private static CacheManager instance = new CacheManager();



    public static CacheManager getInstance() {
        return instance;
    }

    private CacheManager() {
        if (instance != null) {
            throw new RuntimeException("单例不允许多个实例");
        }
    }
}
