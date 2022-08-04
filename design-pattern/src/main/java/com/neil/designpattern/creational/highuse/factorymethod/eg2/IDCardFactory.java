package com.neil.designpattern.creational.highuse.factorymethod.eg2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Decription 生成具体的产品。角色--具体的创建者
 * @Author Huang Chengyi
 * @Date 2022/8/4 13:54
 * @Version 1.0
 */
class IDCardFactory extends Factory {
    private List owners = new ArrayList();

    @Override
    protected Product createProduct(String owner) {
        return new IDCard(owner);
    }

    @Override
    protected void registerProduct(Product product) {
        owners.add(((IDCard) product).getOwner());
    }

    public List getOwners() {
        return owners;
    }
}
