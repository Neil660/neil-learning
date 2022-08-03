package com.neil.designpattern.creational.highuse.builder;

/**
 * @Decription 建造者。负责定义生成实例的API
 * @Author Huang Chengyi
 * @Date 2022/8/2 16:01
 * @Version 1.0
 */
public abstract class Builder {
    public abstract void makeTitle(String title);
    public abstract void makeString(String str);
    public abstract void makeItems(String[] items);
    public abstract void close();
}

