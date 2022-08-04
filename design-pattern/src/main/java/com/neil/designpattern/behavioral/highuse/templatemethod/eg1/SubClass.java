package com.neil.designpattern.behavioral.highuse.templatemethod.eg1;

/**
 * @Decription
 * @Author Huang Chengyi
 * @Date 2022/8/4 16:25
 * @Version 1.0
 */
class SubClass extends Template {
    @Override
    protected void templateMethod() {
        System.out.println("SubClass excuted...");
    }
}
