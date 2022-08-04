package com.neil.designpattern.behavioral.highuse.chainofresponsibility;

/**
 * @Decription 解决问题的具体类，永不处理问题。角色--具体的处理者
 * @Author Huang Chengyi
 * @Date 2022/8/4 14:28
 * @Version 1.0
 */
public class NoSupport extends Support {
    public NoSupport(String name) {
        super(name);
    }

    @Override
    protected boolean resolve(Trouble trouble) {
        return false;
    }
}
