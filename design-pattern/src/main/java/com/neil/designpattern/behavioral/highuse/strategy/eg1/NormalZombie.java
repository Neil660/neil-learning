package com.neil.designpattern.behavioral.highuse.strategy.eg1;

/**
 * @Decription 正常僵尸
 * @Author Huang Chengyi
 * @Date 2022/8/4 15:45
 * @Version 1.0
 */
public class NormalZombie extends Zombie{
    public NormalZombie() {
        super(new StepByStepMoveStrategy(), new BiteArrackStrategy());
    }

    public NormalZombie(IMoveable iMoveable, IAttackable iAttackable) {
        super(iMoveable, iAttackable);
    }

    @Override
    void move() {
        iMoveable.move();
    }

    @Override
    void attack() {
        iAttackable.attack();
    }

    @Override
    public void dispaly() {
        System.out.println("I'm a normal zombie.");
    }
}
