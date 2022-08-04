package com.neil.designpattern.structural.highuse.decorator;

/**
 * @Decription
 * @Author Huang Chengyi
 * @Date 2022/8/4 14:16
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        Component c1 = new ConcreteComponent(); // 生成一个蛋糕
        Component c2 = new ConcreteDecorator1(c1); // 加个草莓
        Component c3 = new ConcreteDecorator2(c2); // 加个奶油
        c1.oper();
        c2.oper();
        c3.oper();

        Component c4 = new ConcreteDecorator2(new ConcreteDecorator1(new ConcreteComponent()));
    }
}
