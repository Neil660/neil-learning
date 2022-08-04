package com.neil.designpattern.structural.highuse.decorator;

/**
 * @Decription 角色--具体的装饰物
 * @Author Huang Chengyi
 * @Date 2022/8/3 15:44
 * @Version 1.0
 */
public class ConcreteDecorator2 extends Decorator {

    public ConcreteDecorator2(Component component) {
        super(component);
    }

    @Override
    public void oper() {
        component.oper();
        System.out.println("添加一个奶油，变成奶油蛋糕");
    }
}
