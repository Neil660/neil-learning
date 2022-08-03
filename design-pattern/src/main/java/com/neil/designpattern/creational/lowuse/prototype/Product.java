package com.neil.designpattern.creational.lowuse.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @Decription 原型实例指定创建对象的种类，并且通过拷贝原型创建新的对象
 * @Author Huang Chengyi
 * @Date 2022/8/3 11:28
 * @Version 1.0
 */
public class Product implements Cloneable, Serializable {
    private String type;
    private BaseInfo baseInfo;

    @Override
    protected Product clone() throws CloneNotSupportedException {
        // 1、第一种深拷贝实现
        // 引用类型BaseInfo作为属性被克隆时，会出现浅拷贝现象。BaseInfo也继承Cloneable后能实现深拷贝
        /*Product clone = (Product) super.clone();
        BaseInfo clone1 = this.baseInfo.clone();
        clone.setBaseInfo(clone1);
        return clone;*/

        // 2、第二种深拷贝实现（流）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            Product product = (Product) ois.readObject();
            return product;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
    public Product() {
    }
    public Product(String type, BaseInfo baseInfo) {
        this.type = type;
        this.baseInfo = baseInfo;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public BaseInfo getBaseInfo() {
        return baseInfo;
    }
    public void setBaseInfo(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }
}
