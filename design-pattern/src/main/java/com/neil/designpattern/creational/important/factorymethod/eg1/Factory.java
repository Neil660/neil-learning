package com.neil.designpattern.creational.important.factorymethod.eg1;

/**
 * @Decription 工厂端
 * @Author Huang Chengyi
 * @Date 2022/8/2 14:32
 * @Version 1.0
 */
public interface Factory {
    Product createProduct();
}

class ConcreateProductAFactory implements Factory {
    @Override
    public Product createProduct() {
        System.out.println("生成产品A");
        return new ProductA();
    }
}

class ConcreateProductBFactory implements Factory {
    @Override
    public Product createProduct() {
        System.out.println("生成产品B");
        return new ProductB();
    }
}