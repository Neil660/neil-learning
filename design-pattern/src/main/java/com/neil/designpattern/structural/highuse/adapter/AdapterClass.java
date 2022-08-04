package com.neil.designpattern.structural.highuse.adapter;

/**
 * @Decription 类类型适配器模式。角色--类适配器
 * @Author Huang Chengyi
 * @Date 2022/8/3 15:33
 * @Version 1.0
 */
public class AdapterClass extends Adaptee implements Target {
    @Override
    public int output5v() {
        int i = output220v();
        // 经过一些列复杂的操作
        return 5;
    }
}
