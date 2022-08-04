package com.neil.designpattern.behavioral.highuse.strategy.eg2;

/**
 * @Decription
 * @Author Huang Chengyi
 * @Date 2022/8/4 15:48
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        int seed1 = 1;
        int seed2 = 2;
        Player p1 = new Player("Taro", new WinningStrategy(seed1));
        Player p2 = new Player("Hana", new ProbStrategy(seed2));
        for (int i = 0; i < 1000; i++) {
            Hand n1 = p1.nextHand();
            Hand n2 = p2.nextHand();
            if (n1.isStrongerThan(n2)) {
                p1.win();
                p2.lose();
            } else if (n1.isWeakerThan(n2)) {
                p1.lose();
                p2.win();
            } else {
                p1.even();
                p2.even();
            }
        }
        System.out.println(p1.toString());
        System.out.println(p2.toString());
    }
}
