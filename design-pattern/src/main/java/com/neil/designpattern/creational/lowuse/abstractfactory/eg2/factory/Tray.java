package com.neil.designpattern.creational.lowuse.abstractfactory.eg2.factory;

import java.util.ArrayList;

/**
 * @Decription 抽象的零件：”托盘“ 一个含有多个Link类和Tray类的容器
 * @Author Huang Chengyi
 * @Date 2022/8/2 14:08
 * @Version 1.0
 */
public abstract class Tray extends Item {
    protected ArrayList tray = new ArrayList();

    public Tray(String caption) {
        super(caption);
    }

    public void add(Item item) {
        tray.add(item);
    }
}
