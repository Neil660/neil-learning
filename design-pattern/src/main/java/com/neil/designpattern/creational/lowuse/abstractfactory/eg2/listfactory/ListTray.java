package com.neil.designpattern.creational.lowuse.abstractfactory.eg2.listfactory;

import com.neil.designpattern.creational.lowuse.abstractfactory.eg2.factory.Tray;

/**
 * @Decription 具体的零件
 * @Author Huang Chengyi
 * @Date 2022/8/2 14:27
 * @Version 1.0
 */
public class ListTray extends Tray {
    public ListTray(String caption) {
        super(caption);
    }

    @Override
    public String makeHTML() {
        StringBuffer sb = new StringBuffer();
        return sb.toString();
    }
}
