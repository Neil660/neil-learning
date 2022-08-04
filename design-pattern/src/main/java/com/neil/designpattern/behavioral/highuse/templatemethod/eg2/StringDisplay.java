package com.neil.designpattern.behavioral.highuse.templatemethod.eg2;

/**
 * @Decription 角色--具体类
 * @Author Huang Chengyi
 * @Date 2022/8/4 16:29
 * @Version 1.0
 */
public class StringDisplay extends AbstractDisplay {
    private String string;

    private int width;

    public StringDisplay(String str) {
        this.string = str;
    }

    @Override
    public void open() {
        printLine();
    }

    @Override
    public void print() {
        System.out.println("|" + string + "|");
    }

    @Override
    public void close() {
        printLine();
    }

    public void printLine() {
        System.out.println("+");
        for (int i = 0; i < width; i++) {
            System.out.println("-");
        } System.out.println("+");
    }
}
