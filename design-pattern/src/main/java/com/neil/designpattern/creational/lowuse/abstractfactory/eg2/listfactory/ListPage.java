package com.neil.designpattern.creational.lowuse.abstractfactory.eg2.listfactory;

import com.neil.designpattern.creational.lowuse.abstractfactory.eg2.factory.Page;

/**
 * @Decription 具体的产品
 * @Author Huang Chengyi
 * @Date 2022/8/2 14:28
 * @Version 1.0
 */
public class ListPage extends Page {

    public ListPage(String title, String author) {
        super(title, author);
    }

    @Override
    public String makeHTML() {
        StringBuffer sb = new StringBuffer();
        return sb.toString();
    }
}
