package com.neil.designpattern.creational.highuse.factorymethod.eg2;

/**
 * @Decription
 * @Author Huang Chengyi
 * @Date 2022/8/2 14:43
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        Factory factory = new IDCardFactory();
        Product card1 = factory.create("jia");
        Product card2 = factory.create("yi");
        card1.use();
        card2.use();
    }
}
