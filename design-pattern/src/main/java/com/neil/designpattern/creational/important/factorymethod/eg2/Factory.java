package com.neil.designpattern.creational.important.factorymethod.eg2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Decription
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