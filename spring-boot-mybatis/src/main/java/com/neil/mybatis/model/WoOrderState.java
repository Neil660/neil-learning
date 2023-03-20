package com.neil.mybatis.model;

import java.io.Serializable;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/20 15:16
 * @Version 1.0
 */
public class WoOrderState implements Serializable {
    private static final long serialVersionUID = -1868507774653154137L;

    private String name;

    private String stateCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
}
