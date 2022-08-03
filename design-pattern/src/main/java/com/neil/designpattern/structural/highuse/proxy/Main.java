package com.neil.designpattern.structural.highuse.proxy;

/**
 * @Decription
 * @Author Huang Chengyi
 * @Date 2022/8/3 15:47
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        Printable p = new PrintProxy("Alice");
        System.out.println("The name is " + p.getPrintName());
        p.setPrintName("Bob");
        System.out.println("Now my name is " + p.getPrintName());
        p.print("Hello World!");
    }
}
