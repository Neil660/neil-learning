package com.neil.designpattern.creational.lowuse.abstractfactory.eg2;

import com.neil.designpattern.creational.lowuse.abstractfactory.eg2.factory.Factory;
import com.neil.designpattern.creational.lowuse.abstractfactory.eg2.factory.Link;
import com.neil.designpattern.creational.lowuse.abstractfactory.eg2.factory.Page;
import com.neil.designpattern.creational.lowuse.abstractfactory.eg2.factory.Tray;

/**
 * @Decription 使用工厂，将零散的零件，组转成产品
 * @Author Huang Chengyi
 * @Date 2022/8/2 14:20
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        Factory factory = Factory.getFactory(args[0]);

        Link people = factory.createLink("人民日报", "https://www.people.com.cn/");
        Link gmw = factory.createLink("光明日报", "https://www.gmw.com.cn/");

        Tray trayNews = factory.createTray("日报");
        trayNews.add(people);
        trayNews.add(gmw);

        Page page = factory.createPage("Link Page", "xxx");
        page.add(trayNews);
        page.output();
    }
}
