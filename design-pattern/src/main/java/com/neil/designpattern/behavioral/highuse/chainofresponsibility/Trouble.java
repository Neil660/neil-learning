package com.neil.designpattern.behavioral.highuse.chainofresponsibility;

/**
 * @Decription 带解决的问题。角色--问题
 * @Author Huang Chengyi
 * @Date 2022/8/4 14:28
 * @Version 1.0
 */
public class Trouble {
    private int number; //问题编号

    public Trouble(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public String toString() {
        return "Trouble number=" + number;
    }
}
