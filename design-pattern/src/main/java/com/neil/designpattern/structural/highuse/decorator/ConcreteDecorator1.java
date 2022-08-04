package com.neil.designpattern.structural.highuse.decorator;

/**
 * @Decription 角色--具体的装饰物
 * @Author Huang Chengyi
 * @Date 2022/8/3 15:44
 * @Version 1.0
 */
public class ConcreteDecorator1 extends Decorator {

    public ConcreteDecorator1(Component component) {
        super(component);
    }

    @Override
    public void oper() {
        component.oper();
        System.out.println("添加美一个草莓，变成草莓蛋糕");
    }
}
