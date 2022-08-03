package com.neil.designpattern.structural.highuse.proxy;

/**
 * @Decription 打印机，实际的主体，本人
 * 会在代理PrintProxy无法胜任工作时出场
 * @Author Huang Chengyi
 * @Date 2022/8/3 15:46
 * @Version 1.0
 */
public class Printer implements Printable {
    private String name;
    public Printer(String name) {
        this.name = name;
    }

    @Override
    public void setPrintName(String name) {
        this.name = name;
    }

    @Override
    public String getPrintName() {
        return name;
    }

    @Override
    public void print(String str) {
        System.out.println(name);
        System.out.println(str);
    }
}
