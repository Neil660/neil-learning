package com.neil.redis.controller;

import com.alibaba.fastjson.JSONObject;
import com.neil.redis.model.SysLog;
import com.neil.redis.service.SysLogService;
import com.neil.redis.dao.RedisDefaultIDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/11/17 17:18
 * @Version 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    SysLogService sysLogService;
    @Autowired
    RedisDefaultIDao redisDefaultIDao;

    @RequestMapping("/addToRedis/{k}")
    public void addToRedis(@PathVariable String k) {
        try {
            redisDefaultIDao.setEx(k, "value-" + k);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/getFromRedis/{k}")
    public String getFromRedis(@PathVariable String k) {
        return redisDefaultIDao.readObject(k, String.class);
    }

    @RequestMapping("/add")
    public int addSysLog(@RequestBody JSONObject query) {
        return sysLogService.addSysLog(query);
    }

    @RequestMapping("/del/{id}")
    public int delSysLog(@PathVariable int id) {
        return sysLogService.delSysLog(id);
    }

    @RequestMapping("/update")
    @ResponseBody
    public int updateSysLog(@RequestBody JSONObject query) {
        return sysLogService.updateSysLog(query);
    }

    @RequestMapping("/get")
    public List<SysLog> getAllSysLog() {
        return sysLogService.getAllSysLog();
    }

    @RequestMapping("/get/{id}")
    public SysLog getSysLogById(@PathVariable int id) {
        return sysLogService.getSysLogById(id);
    }
}
