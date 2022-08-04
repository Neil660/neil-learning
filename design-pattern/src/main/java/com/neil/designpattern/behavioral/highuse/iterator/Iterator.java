package com.neil.designpattern.behavioral.highuse.iterator;

/**
 * @Decription 遍历集合的接口。角色--迭代器
 * @Author Huang Chengyi
 * @Date 2022/8/4 14:50
 * @Version 1.0
 */
public interface Iterator {
    public abstract boolean hasNext();

    public abstract Object next();
}
