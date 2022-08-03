package com.neil.designpattern.creational.highuse.builder;

/**
 * @Decription 具体的建造者。
 * @Author Huang Chengyi
 * @Date 2022/8/3 16:20
 * @Version 1.0
 */
public class HTMLBuilder extends Builder {
    private String filename;

    @Override
    public void makeTitle(String title) {

    }

    @Override
    public void makeString(String str) {

    }

    @Override
    public void makeItems(String[] items) {

    }

    @Override
    public void close() {

    }

    public String getResult() {
        return filename;
    }
}
