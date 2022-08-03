package com.neil.designpattern.structural.highuse.decorator;

/**
 * @Decription 装饰器
 * @Author Huang Chengyi
 * @Date 2022/8/3 15:41
 * @Version 1.0
 */
public abstract class Decorator implements Component {

    Component component;

    public Decorator(Component component) {
        this.component = component;
    }
}
