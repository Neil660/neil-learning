package com.neil.designpattern.creational.highuse.factorymethod.eg2;

/**
 * @Decription 实现了use方法的类。角色--具体的产品
 * @Author Huang Chengyi
 * @Date 2022/8/4 13:54
 * @Version 1.0
 */
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
