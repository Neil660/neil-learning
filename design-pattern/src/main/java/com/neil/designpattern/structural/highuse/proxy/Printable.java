package com.neil.designpattern.structural.highuse.proxy;

/**
 * @Decription 公共接口，主体
 * @Author Huang Chengyi
 * @Date 2022/8/3 15:46
 * @Version 1.0
 */
public interface Printable {
    public abstract void setPrintName(String name);
    public abstract String getPrintName();
    public abstract void print(String str);
}
