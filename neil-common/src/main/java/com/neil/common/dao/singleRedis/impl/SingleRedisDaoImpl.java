package com.neil.common.dao.singleRedis.impl;

import com.neil.common.dao.singleRedis.SingleRedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Decription
 * @Author Huang Chengyi
 * @Date 2022/8/19 17:01
 * @Version 1.0
 */
public class SingleRedisDaoImpl implements SingleRedisDao {
    @Autowired
    private RedisTemplate redisTemplate;
}
