package com.neil.spring.boot.redis.service.impl;

import com.neil.spring.boot.redis.dao.SysLogDao;
import com.neil.spring.boot.redis.service.CacheRefreshService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/11/20 17:59
 * @Version 1.0
 */
public class CacheRefreshServiceImpl implements CacheRefreshService {
    @Autowired
    SysLogDao sysLogDao;

    @Override
    public void refresh() {
        sysLogDao.refreshGetAllSysLog();
        sysLogDao.refreshGetSysLogById();
    }
}
