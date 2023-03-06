package com.neil.spring.aop.dao;

import com.neil.spring.aop.model.SysLog;

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

    SysLog getSysLogById(int id);
}
