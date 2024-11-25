package com.neil.cycledepend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Decription 循环依赖演示类
 * @Author NEIL
 * @Date 2023/3/20 21:40
 * @Version 1.0
 */
@Component
public class CycleA {
    @Autowired
    private CycleB cycleB;

    public CycleB getCycleB() {
        return cycleB;
    }

    public void setCycleB(CycleB cycleB) {
        this.cycleB = cycleB;
    }
}
