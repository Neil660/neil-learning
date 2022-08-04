package com.neil.designpattern.behavioral.highuse.chainofresponsibility;

/**
 * @Decription 解决问题的具体类，只解决特定编号的问题。角色--具体的处理者
 * @Author Huang Chengyi
 * @Date 2022/8/4 14:29
 * @Version 1.0
 */
public class SpecialSupport extends Support {
    private int number;

    public SpecialSupport(String name, int number) {
        super(name);
        this.number = number;
    }

    @Override
    protected boolean resolve(Trouble trouble) {
        if (trouble.getNumber() == number) {
            return true;
        } else {
            return false;
        }
    }
}
