package com.neil.designpattern.structural.highuse.decorator;

/**
 * @Decription 角色--具体的组件
 * @Author Huang Chengyi
 * @Date 2022/8/3 15:42
 * @Version 1.0
 */
public class ConcreteComponent implements Component {
    @Override
    public void oper() {
        System.out.println("生成一个蛋糕");
    }
}
