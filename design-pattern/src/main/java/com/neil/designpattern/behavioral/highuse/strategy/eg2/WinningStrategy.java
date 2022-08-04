package com.neil.designpattern.behavioral.highuse.strategy.eg2;

import java.util.Random;

/**
 * @Decription 这局获胜，下局出一样的手势的策略。角色--具体的策略
 * @Author Huang Chengyi
 * @Date 2022/8/4 15:47
 * @Version 1.0
 */
public class WinningStrategy implements Strategy {
    private Random random;

    private boolean won = false;

    private Hand preHand;

    public WinningStrategy(int seed) {
        random = new Random(seed);
    }

    public Hand nextHand() {
        if (!won) {
            preHand = Hand.getHand(random.nextInt(3));
        }
        return preHand;
    }

    public void study(boolean win) {
        won = win;
    }
}
