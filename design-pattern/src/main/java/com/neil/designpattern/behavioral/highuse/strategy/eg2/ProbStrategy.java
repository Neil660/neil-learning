package com.neil.designpattern.behavioral.highuse.strategy.eg2;

import java.util.Random;

/**
 * @Decription 根据概率出拳的策略。角色--具体的策略
 * @Author Huang Chengyi
 * @Date 2022/8/4 15:47
 * @Version 1.0
 */
public class ProbStrategy implements Strategy {
    private Random random;

    private int preHandValue = 0;

    private int currentHandValue = 0;

    private int[][] history = {{1, 1, 1},{1, 1, 1},{1, 1, 1}};

    public ProbStrategy(int seed) {
        random = new Random(seed);
    }

    public Hand nextHand() {
        int bet = random.nextInt(getSum(currentHandValue));
        int handValue = 0;
        if (bet < history[currentHandValue][0]) {
            handValue = 0;
        } else if (bet < history[currentHandValue][0] + history[currentHandValue][1]) {
            handValue = 1;
        } else {
            handValue = 2;
        }
        preHandValue = currentHandValue;
        currentHandValue = handValue;
        return Hand.getHand(handValue);
    }

    public int getSum(int hv) {
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            sum += history[hv][i];
        }
        return sum;
    }

    public void study(boolean win) {
        if (win) {
            history[preHandValue][currentHandValue]++;
        } else {
            history[preHandValue][(currentHandValue + 1) % 3]++;
            history[preHandValue][(currentHandValue + 2) % 3]++;
        }
    }
}
