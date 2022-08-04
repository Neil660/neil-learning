package com.neil.designpattern.behavioral.highuse.strategy.eg2;

/**
 * @Decription 策略。角色--策略
 * @Author Huang Chengyi
 * @Date 2022/8/4 15:46
 * @Version 1.0
 */
public interface Strategy {
    public abstract Hand nextHand();

    public abstract void study(boolean win);
}
