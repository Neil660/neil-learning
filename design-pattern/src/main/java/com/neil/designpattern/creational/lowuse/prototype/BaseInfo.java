package com.neil.designpattern.creational.lowuse.prototype;

import java.io.Serializable;

/**
 * @Decription
 * @Author Huang Chengyi
 * @Date 2022/8/3 11:29
 * @Version 1.0
 */
public class BaseInfo implements Cloneable, Serializable {
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BaseInfo() { }
    public BaseInfo(String name) {
        this.name = name;
    }
    @Override
    protected BaseInfo clone() throws CloneNotSupportedException {
        return (BaseInfo) super.clone();
    }
}
