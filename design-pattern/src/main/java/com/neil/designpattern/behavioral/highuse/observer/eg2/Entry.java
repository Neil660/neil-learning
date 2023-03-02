package com.neil.designpattern.behavioral.highuse.observer.eg2;

import java.util.Iterator;

/**
 * @Decription File、Director的父类
 * @Author NEIL
 * @Date 2023/2/9 10:24
 * @Version 1.0
 */
public abstract class Entry implements Element {
    public abstract String getName();

    public abstract int getSize();

    public Entry add(Entry entry) throws FileTreatementException {
        throw new FileTreatementException();
    }

    public Iterator iterator() throws FileTreatementException {
        throw new FileTreatementException();
    }

    public String toString() {
        return getName() + " (" + getSize() + ")";
    }
}
