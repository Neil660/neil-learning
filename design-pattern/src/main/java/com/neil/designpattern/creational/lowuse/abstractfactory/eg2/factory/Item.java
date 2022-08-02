package com.neil.designpattern.creational.lowuse.abstractfactory.eg2.factory;

/**
 * @Decription 抽象的零件：”项目“
 * @Author Huang Chengyi
 * @Date 2022/8/2 14:06
 * @Version 1.0
 */
public abstract class Item {
    protected String caption;

    public Item(String caption) {
        this.caption = caption;
    }

    public abstract String makeHTML();
}
