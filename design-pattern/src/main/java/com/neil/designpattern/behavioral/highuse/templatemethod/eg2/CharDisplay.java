package com.neil.designpattern.behavioral.highuse.templatemethod.eg2;

/**
 * @Decription 角色--具体类
 * @Author Huang Chengyi
 * @Date 2022/8/4 16:29
 * @Version 1.0
 */
public class CharDisplay extends AbstractDisplay {
    private char ch;

    public CharDisplay(char ch) {
        this.ch = ch;
    }

    @Override
    public void open() {
        System.out.println("<<");
    }

    @Override
    public void print() {
        System.out.println(ch);
    }

    @Override
    public void close() {
        System.out.println(">>");
    }
}
