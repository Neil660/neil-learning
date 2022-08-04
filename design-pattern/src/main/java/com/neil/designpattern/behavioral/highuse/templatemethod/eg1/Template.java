package com.neil.designpattern.behavioral.highuse.templatemethod.eg1;

/**
 * @Decription 定义一个操作的算法骨架，而将一些操作延迟到子类中，使得子类可以不改变算法的结构即可重定义该算法的某些步骤。
 * @Author Huang Chengyi
 * @Date 2022/8/4 16:25
 * @Version 1.0
 */
public abstract class Template {
    public void operation() {
        System.out.println("step1");
        System.out.println("step2");
        templateMethod();
    }
    abstract protected void templateMethod();
}

