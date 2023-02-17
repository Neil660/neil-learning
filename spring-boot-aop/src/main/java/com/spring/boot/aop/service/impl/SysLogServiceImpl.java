package com.spring.boot.aop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.spring.boot.aop.dao.SysLogDao;
import com.spring.boot.aop.model.SysLog;
import com.spring.boot.aop.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/11/17 17:17
 * @Version 1.0
 */
@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    SysLogDao sysLogDao;

    @Override
    public int addSysLog(JSONObject query) {
        SysLog sysLog = new SysLog();
        sysLog.setId(query.getInteger("id"));
        sysLog.setCreateTime(new Date());
        sysLog.setIp(query.getString("ip"));
        sysLog.setMethod("addSysLog");
        sysLog.setOperation("ADD");
        sysLog.setUsername(query.getString("username"));
        return sysLogDao.addSysLog(sysLog);
    }

    @Override
    public int delSysLog(int id) {
        return sysLogDao.delSysLog(id);
    }

    @Override
    public int updateSysLog(JSONObject query) {
        SysLog sysLog = new SysLog();
        sysLog.setId(query.getInteger("id"));
        sysLog.setCreateTime(new Date());
        sysLog.setIp(query.getString("ip"));
        sysLog.setMethod("updateSysLog");
        sysLog.setOperation("UPDATE");
        sysLog.setUsername(query.getString("username"));
        return sysLogDao.updateSysLog(sysLog);
    }

    @Override
    public List<SysLog> getAllSysLog() {
        return sysLogDao.getAllSysLog();
    }

    @Override
    public SysLog getSysLogById(int id) {
        return sysLogDao.getSysLogById(id);
    }
}
