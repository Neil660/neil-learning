package com.neil.designpattern.behavioral.highuse.observer.eg2;

/**
 * @Decription 表示数据结构的接口，它接受访问者的访问
 * @Author NEIL
 * @Date 2023/2/9 10:23
 * @Version 1.0
 */
public interface Element {
    public abstract void accept(Visitor v);
}
