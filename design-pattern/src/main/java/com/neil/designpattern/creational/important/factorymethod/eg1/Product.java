package com.neil.designpattern.creational.important.factorymethod.eg1;

/**
 * @Decription 产品端
 * @Author Huang Chengyi
 * @Date 2022/8/2 14:33
 * @Version 1.0
 */
public abstract class Product {
    abstract void print();
}

class ProductA extends Product {
    @Override
    void print() {
        System.out.println("我是产品A");
    }
}

class ProductB extends Product {
    @Override
    void print() {
        System.out.println("我是产品B");
    }
}
