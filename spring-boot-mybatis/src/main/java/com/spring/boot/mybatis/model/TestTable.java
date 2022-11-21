package com.spring.boot.mybatis.model;

import java.io.Serializable;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/9/29 15:48
 * @Version 1.0
 */
public class TestTable implements Serializable {

    private static final long serialVersionUID = 4739911983547116763L;

    private long id;

    private String action;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestTable:id=[" + this.id + "],action=[" + this.action + "],name=[" + this.name + "]";
    }
}
