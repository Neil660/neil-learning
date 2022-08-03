package com.neil.designpattern.creational.highuse.builder;

/**
 * @Decription
 * @Author Huang Chengyi
 * @Date 2022/8/2 16:05
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        TextBuilder tb = new TextBuilder();
        Director director = new Director(tb);
        director.construct();
        System.out.println(tb.getResult());

        HTMLBuilder hb = new HTMLBuilder();
        Director director2 = new Director(hb);
        director2.construct();
        System.out.println(hb.getResult());
    }
}
