package com.neil.designpattern.behavioral.highuse.templatemethod.eg2;

/**
 * @Decription 角色--抽象类
 * @Author Huang Chengyi
 * @Date 2022/8/4 16:29
 * @Version 1.0
 */
public abstract class AbstractDisplay {
    public abstract void open();
    public abstract void print();
    public abstract void close();
    public final void display() {
        open();
        for (int i = 0; i < 5; i++) {
            print();
        }
        close();
    }
}
