package com.neil.designpattern.behavioral.highuse.chainofresponsibility;

/**
 * @Decription 解决问题的抽象类。角色--处理者
 * @Author Huang Chengyi
 * @Date 2022/8/4 14:28
 * @Version 1.0
 */
public abstract class Support {
    private String name;

    private Support next;

    public Support(String name) {
        this.name = name;
    }

    // 要推卸给的对象
    public Support setNext(Support next) {
        this.next = next;
        return next;
    }

    //解决问题的步骤
    public final void support(Trouble trouble) {
        if (resolve(trouble)) {
            done(trouble);
        } else if (next != null) {
            next.support(trouble);
        } else {
            fail(trouble);
        }
    }

    public String toString() {
        return this.name;
    }

    protected abstract boolean resolve(Trouble trouble);

    protected void done(Trouble trouble) {
        System.out.println("解决问题了");
    }

    protected void fail(Trouble trouble) {
        System.out.println("未解决问题");
    }
}
