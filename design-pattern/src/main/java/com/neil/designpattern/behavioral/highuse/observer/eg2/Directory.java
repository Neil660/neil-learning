package com.neil.designpattern.behavioral.highuse.observer.eg2;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @Decription 文件夹类
 * @Author NEIL
 * @Date 2023/2/9 10:24
 * @Version 1.0
 */
public class Directory extends Entry {
    private String name;
    private ArrayList dir = new ArrayList();

    public Directory(String name) {
        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        int size = 0;
        Iterator iterator = dir.iterator();
        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            size += entry.getSize();
        }
        return size;
    }

    public Entry add(Entry entry) {
        dir.add(entry);
        return this;
    }

    public Iterator iterator() {
        return dir.iterator();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
