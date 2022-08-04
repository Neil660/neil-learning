package com.neil.designpattern.behavioral.highuse.templatemethod.eg2;

/**
 * @Decription
 * @Author Huang Chengyi
 * @Date 2022/8/4 16:34
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        AbstractDisplay as = new CharDisplay('H');
        AbstractDisplay ad = new StringDisplay("He");
        as.display();
        ad.display();
    }
}
