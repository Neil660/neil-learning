package com.neil.designpattern.creational.highuse.factorymethod.eg2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Decription 生成product的抽象类，角色--创建者（框架）
 * @Author Huang Chengyi
 * @Date 2022/8/2 14:36
 * @Version 1.0
 */
public abstract class Factory {
    public final Product create(String owner) {
        Product p = createProduct(owner);
        registerProduct(p);
        return p;
    }

    protected abstract Product createProduct(String owner);
    protected abstract void registerProduct(Product product);
}

