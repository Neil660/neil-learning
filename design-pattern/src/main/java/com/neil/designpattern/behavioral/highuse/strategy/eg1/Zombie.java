package com.neil.designpattern.behavioral.highuse.strategy.eg1;

/**
 * @Decription 僵尸抽象类
 * @Author Huang Chengyi
 * @Date 2022/8/4 15:44
 * @Version 1.0
 */
public abstract class Zombie {
    IMoveable iMoveable;

    IAttackable iAttackable;

    abstract void move();

    abstract void attack();

    public abstract void dispaly();

    public Zombie(IMoveable iMoveable, IAttackable iAttackable) {
        this.iMoveable = iMoveable;
        this.iAttackable = iAttackable;
    }

    public IMoveable getiMoveable() {
        return iMoveable;
    }

    public void setiMoveable(IMoveable iMoveable) {
        this.iMoveable = iMoveable;
    }

    public IAttackable getiAttackable() {
        return iAttackable;
    }

    public void setiAttackable(IAttackable iAttackable) {
        this.iAttackable = iAttackable;
    }
}
