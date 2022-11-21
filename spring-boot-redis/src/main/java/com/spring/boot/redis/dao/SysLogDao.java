package com.spring.boot.redis.dao;

import com.spring.boot.redis.model.SysLog;

import java.util.List;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/11/17 17:16
 * @Version 1.0
 */
public interface SysLogDao {
    int addSysLog(SysLog sysLog);

    int delSysLog(int id);

    int updateSysLog(SysLog sysLog);

    List<SysLog> getAllSysLog();

    void refreshGetAllSysLog();

    SysLog cacheUpdateGetSysLogById(SysLog sysLog);

    SysLog getSysLogById(int id);

    void refreshGetSysLogById();
}
