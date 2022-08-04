package com.neil.designpattern.behavioral.highuse.strategy.eg1;

/**
 * @Decription
 * @Author Huang Chengyi
 * @Date 2022/8/4 15:44
 * @Version 1.0
 */
public class HitArrackStrategy implements IAttackable {
    @Override
    public void attack() {
        System.out.println("打");
    }
}
