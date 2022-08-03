package com.neil.designpattern.creational.highuse.builder;

/**
 * @Decription 监工。负责使用Builder角色的API来生成实例
 * @Author Huang Chengyi
 * @Date 2022/8/2 14:02
 * @Version 1.0
 */
public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public void construct() {
        builder.makeTitle("写个标题");
        builder.makeString("字符串");
        builder.makeItems(new String[] {"第一章", "第二章"});
        builder.close(); // 完成
    }
}
