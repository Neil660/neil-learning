package com.neil.designpattern.behavioral.highuse.strategy.eg1;

/**
 * @Decription
 * @Author Huang Chengyi
 * @Date 2022/8/4 15:43
 * @Version 1.0
 */
public class StepByStepMoveStrategy implements IMoveable {
    @Override
    public void move() {
        System.out.println("一步一步移动");
    }
}
