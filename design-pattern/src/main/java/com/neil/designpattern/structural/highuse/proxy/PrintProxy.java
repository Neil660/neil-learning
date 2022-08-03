package com.neil.designpattern.structural.highuse.proxy;

/**
 * @Decription 打印机的代理
 * 尽量处理来自客户的需求，只有当自己不能处理时，才会交给实际主体Printer处理，只有在必要时才会生成Printer
 * @Author Huang Chengyi
 * @Date 2022/8/3 15:47
 * @Version 1.0
 */
public class PrintProxy implements Printable {
    private String name;
    private Printer real; // 本人

    public PrintProxy() {}

    public PrintProxy(String name) {
        this.name = name;
    }

    @Override
    public synchronized void setPrintName(String name) {
        if (real != null) {
            real.setPrintName(name);
        }
        this.name = name;
    }

    @Override
    public String getPrintName() {
        return name;
    }

    @Override
    public void print(String str) {
        // 将耗时的处理（生成实例）延迟到print被调用后才进行
        realize();
        real.print(str); //委托给本人处理
    }

    private synchronized void realize() {
        if (real == null) {
            real = new Printer(name);
        }
    }
}
