package com.neil.redis.service.impl;

import com.neil.redis.service.CacheRefreshService;
import com.neil.redis.dao.SysLogDao;
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
