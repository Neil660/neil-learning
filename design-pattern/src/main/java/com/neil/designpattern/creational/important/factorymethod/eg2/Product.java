package com.neil.designpattern.creational.important.factorymethod.eg2;

/**
 * @Decription
 * @Author Huang Chengyi
 * @Date 2022/8/2 14:36
 * @Version 1.0
 */
public abstract class Product {
    abstract void use();
}

class IDCard extends Product {
    private String owner;

    IDCard(String owner) {
        System.out.println("制作" + owner + "的ID卡");
        this.owner = owner;
    }

    @Override
    void use() {
        System.out.println("使用" + owner + "的ID卡");
    }

    public String getOwner() {
        return owner;
    }
}
