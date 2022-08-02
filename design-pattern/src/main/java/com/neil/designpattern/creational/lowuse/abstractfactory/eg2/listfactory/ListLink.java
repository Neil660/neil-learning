package com.neil.designpattern.creational.lowuse.abstractfactory.eg2.listfactory;

import com.neil.designpattern.creational.lowuse.abstractfactory.eg2.factory.Link;

/**
 * @Decription 具体的零件
 * @Author Huang Chengyi
 * @Date 2022/8/2 14:25
 * @Version 1.0
 */
public class ListLink extends Link {
    public ListLink(String caption, String url) {
        super(caption, url);
    }

    @Override
    public String makeHTML() {
        return "    <li><a href=\"" + url + "\">" + caption + "</a></li>\n";
    }
}
