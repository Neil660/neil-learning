package com.neil.designpattern.behavioral.highuse.observer.eg1;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @Decription 角色--观察对象
 * @Author Huang Chengyi
 * @Date 2022/8/4 16:37
 * @Version 1.0
 */
public abstract class NumberGenerator {
    private ArrayList observers = new ArrayList();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObserver () {
        Iterator iterator = observers.iterator();
        while (iterator.hasNext()) {
            Observer observer = (Observer) iterator.next();
            observer.update(this);
        }
    }

    public abstract int getNumber();

    public abstract void execute();
}
