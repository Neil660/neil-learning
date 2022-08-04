package com.neil.designpattern.structural.lowuse.flyweight;

/**
 * @Decription 角色--轻量级
 * @Author Huang Chengyi
 * @Date 2022/8/4 15:22
 * @Version 1.0
 */
public class Tree {
    private final String name;

    private final String data;

    public Tree(String name, String data) {
        this.name = name;
        this.data = data;
    }
}
