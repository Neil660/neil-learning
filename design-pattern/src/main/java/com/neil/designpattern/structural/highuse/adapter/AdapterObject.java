package com.neil.designpattern.structural.highuse.adapter;

/**
 * @Decription 对象类型适配器模式
 * @Author Huang Chengyi
 * @Date 2022/8/3 15:33
 * @Version 1.0
 */
public class AdapterObject implements Target {
    private Adapter adapter;

    public AdapterObject(Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int output5v() {
        int i = adapter.output220v();
        // 经过一些列复杂的操作
        return 5;
    }
}
