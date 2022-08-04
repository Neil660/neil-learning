package com.neil.designpattern.behavioral.highuse.chainofresponsibility;

/**
 * @Decription 解决问题的具体类，只解决奇数编号的问题。角色--具体的处理者
 * @Author Huang Chengyi
 * @Date 2022/8/4 14:29
 * @Version 1.0
 */
public class OddSupport extends Support {
    public OddSupport(String name) {
        super(name);
    }

    @Override
    protected boolean resolve(Trouble trouble) {
        if (trouble.getNumber() % 2 == 1) {
            return true;
        } else {
            return false;
        }
    }
}
