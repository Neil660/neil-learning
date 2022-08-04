package com.neil.designpattern.structural.lowuse.facade;

/**
 * @Decription 不需要关注subsystem的实现
 * @Author Huang Chengyi
 * @Date 2022/8/4 15:26
 * @Version 1.0
 */
public class Client1 {
    Facade facade = new Facade();
    public void doSomething() {
        facade.doSomething();
    }
}
