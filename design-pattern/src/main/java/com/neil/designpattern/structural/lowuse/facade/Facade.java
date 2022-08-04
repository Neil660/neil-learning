package com.neil.designpattern.structural.lowuse.facade;

/**
 * @Decription 角色--窗口
 * @Author Huang Chengyi
 * @Date 2022/8/4 15:25
 * @Version 1.0
 */
public class Facade {
    SubSystem1 subSystem1 = new SubSystem1();
    SubSystem2 subSystem2 = new SubSystem2();
    public void doSomething() {
        subSystem1.method();
        subSystem2.method();
    }
}

class SubSystem1 {
    void method() {
        System.out.println("subSystem1 start.");
    }
}

class SubSystem2 {
    void method() {
        System.out.println("subSystem1 start.");
    }
}
