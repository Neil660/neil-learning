package com.neil.designpattern.creational.lowuse.abstractfactory.eg2.listfactory;

import com.neil.designpattern.creational.lowuse.abstractfactory.eg2.factory.Factory;
import com.neil.designpattern.creational.lowuse.abstractfactory.eg2.factory.Link;
import com.neil.designpattern.creational.lowuse.abstractfactory.eg2.factory.Page;
import com.neil.designpattern.creational.lowuse.abstractfactory.eg2.factory.Tray;

/**
 * @Decription 具体的工厂
 * @Author Huang Chengyi
 * @Date 2022/8/2 14:25
 * @Version 1.0
 */
public class ListFactory extends Factory {
    @Override
    public Link createLink(String caption, String url) {
        return new ListLink(caption, url);
    }

    @Override
    public Tray createTray(String caption) {
        return new ListTray(caption);
    }

    @Override
    public Page createPage(String title, String author) {
        return new ListPage(title, author);
    }
}
