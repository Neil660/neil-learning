package com.neil.designpattern.behavioral.highuse.observer.eg2;

/**
 * @Decription 访问者的抽象类，访问文件和文件夹
 * @Author NEIL
 * @Date 2023/2/9 10:23
 * @Version 1.0
 */
public abstract class Visitor {
    public abstract void visit(File file);
    public abstract void visit(Directory directory);
}
