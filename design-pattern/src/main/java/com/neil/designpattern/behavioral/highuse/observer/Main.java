package com.neil.designpattern.behavioral.highuse.observer;

/**
 * @Decription
 * @Author Huang Chengyi
 * @Date 2022/8/4 16:38
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        NumberGenerator numberGenerator = new RandomNumberGenerator();
        Observer o1 = new DigitObserver();
        Observer o2 = new GraphObserver();
        numberGenerator.addObserver(o1);
        numberGenerator.addObserver(o2);
        numberGenerator.execute();
    }
}
