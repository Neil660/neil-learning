package com.neil.spring.boot.aop.dao;

import com.alibaba.fastjson.JSONObject;
import com.neil.spring.boot.aop.model.SysLog;
import org.springframework.web.bind.annotation.RequestBody;

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
