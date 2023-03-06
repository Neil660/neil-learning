package com.neil.spring.aop.service;

import com.alibaba.fastjson.JSONObject;
import com.neil.spring.aop.model.SysLog;

import java.util.List;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/11/17 17:17
 * @Version 1.0
 */
public interface SysLogService {
    int addSysLog(JSONObject query);

    int delSysLog(int id);

    int updateSysLog(JSONObject jsonObject);

    List<SysLog> getAllSysLog();

    SysLog getSysLogById(int id);
}
