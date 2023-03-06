package com.neil.redis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/11/17 17:15
 * @Version 1.0
 */
public class SysLog implements Serializable {
    private static final long serialVersionUID = -2197528564689272022L;
    private Integer id;
    private String username;
    private String operation;
    private Integer time;
    private String method;
    private String params;
    private String ip;
    private Date createTime;

    public SysLog() {};

    public SysLog(Integer id, String username, String operation, Integer time, String method, String params, String ip, Date createTime) {
        this.id = id;
        this.username = username;
        this.operation = operation;
        this.time = time;
        this.method = method;
        this.params = params;
        this.ip = ip;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
