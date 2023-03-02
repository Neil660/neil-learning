package com.neil.designpattern.behavioral.highuse.observer.eg2;

/**
 * @Decription 文件类
 * @Author NEIL
 * @Date 2023/2/9 10:24
 * @Version 1.0
 */
public class File extends Entry {
    private String name;
    private int size;
    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
