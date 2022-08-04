package com.neil.designpattern.behavioral.highuse.observer;

/**
 * @Decription 角色--具体的观察者
 * @Author Huang Chengyi
 * @Date 2022/8/4 16:38
 * @Version 1.0
 */
public class DigitObserver implements Observer {
    @Override
    public void update(NumberGenerator numberGenerator) {
        System.out.println("DigitObserver number=" + numberGenerator.getNumber());
        try {
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
