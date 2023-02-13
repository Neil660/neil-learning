package com.neil.designpattern.behavioral.highuse.observer.eg1;

import java.util.Random;

/**
 * @Decription 角色--具体的观察对象
 * @Author Huang Chengyi
 * @Date 2022/8/4 16:38
 * @Version 1.0
 */
public class RandomNumberGenerator extends NumberGenerator {
    private Random random = new Random();

    private int number;

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void execute() {
        for (int i = 0; i < 20; i++) {
            number = random.nextInt(50);
            notifyObserver();
        }
    }
}
