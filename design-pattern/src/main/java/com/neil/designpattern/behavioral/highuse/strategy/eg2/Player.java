package com.neil.designpattern.behavioral.highuse.strategy.eg2;

/**
 * @Decription 猜拳选手。角色--上下文
 * @Author Huang Chengyi
 * @Date 2022/8/4 15:48
 * @Version 1.0
 */
public class Player {
    private String name;

    private Strategy strategy;

    private int winCount;

    private int loseCount;

    private int gameCount;

    public Player(String name, Strategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    public Hand nextHand() {
        return strategy.nextHand();
    }

    public void win() {
        strategy.study(true);
        winCount++;
        gameCount++;
    }

    public void lose() {
        strategy.study(false);
        loseCount++;
        gameCount++;
    }

    public void even() {
        gameCount++;
    }

    public String toString() {
        return name + ":" + gameCount + " games, " + winCount + " win, " + loseCount + " lose.";
    }
}
