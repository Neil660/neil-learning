package com.neil.designpattern.creational.lowuse.abstractfactory.eg2.factory;

/**
 * @Decription 抽象的零件：抽象表示超链接的类
 * @Author Huang Chengyi
 * @Date 2022/8/2 14:07
 * @Version 1.0
 */
public abstract class Link extends Item {
    protected String url;

    public Link(String caption, String url) {
        super(caption);
        this.url = url;
    }
}
