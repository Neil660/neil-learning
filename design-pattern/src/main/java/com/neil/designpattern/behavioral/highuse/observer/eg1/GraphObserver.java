package com.neil.designpattern.behavioral.highuse.observer.eg1;

/**
 * @Decription 角色--具体的观察者
 * @Author Huang Chengyi
 * @Date 2022/8/4 16:38
 * @Version 1.0
 */
public class GraphObserver implements Observer {
    @Override
    public void update(NumberGenerator numberGenerator) {
        System.out.print("GraphObserver");
        for (int i = 0; i < numberGenerator.getNumber(); i++) {
            System.out.println("*");
        }
        try {
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
