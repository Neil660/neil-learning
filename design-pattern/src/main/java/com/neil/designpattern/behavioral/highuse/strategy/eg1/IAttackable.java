package com.neil.designpattern.behavioral.highuse.strategy.eg1;

/**
 * @Decription 攻击方式算法族
 * 定义了算法族，分别封装了起来，让它们之间可以互相替换，此模式的变化独立于算法的使用者。要分析出稳定的部分和变化的部分，
 * @Author Huang Chengyi
 * @Date 2022/8/4 15:44
 * @Version 1.0
 */
public interface IAttackable {
    void attack();
}
