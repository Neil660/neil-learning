package com.neil.designpattern.behavioral.highuse.chainofresponsibility;

/**
 * @Decription 解决问题的具体类，仅解决编号小于特定值的问题。角色--具体的处理者
 * @Author Huang Chengyi
 * @Date 2022/8/4 14:29
 * @Version 1.0
 */
public class LimitSupport extends Support {
    private int limit;

    public LimitSupport(String name, int limit) {
        super(name);
        this.limit = limit;
    }

    @Override
    protected boolean resolve(Trouble trouble) {
        if (trouble.getNumber() < limit) {
            return true;
        } else {
            return false;
        }
    }
}
