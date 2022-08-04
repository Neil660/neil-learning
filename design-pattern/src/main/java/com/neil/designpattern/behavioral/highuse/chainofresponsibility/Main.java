package com.neil.designpattern.behavioral.highuse.chainofresponsibility;

/**
 * @Decription
 * @Author Huang Chengyi
 * @Date 2022/8/4 14:29
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        Support s1 = new NoSupport("s1");
        Support s2 = new NoSupport("s2");
        Support s3 = new LimitSupport("s3", 5);
        Support s4 = new OddSupport("s4");
        Support s5 = new SpecialSupport("s5", 0);

        //形成责任链
        s1.setNext(s2.setNext(s3.setNext(s4.setNext(s5))));
        for (int i = 0; i < 10; i++) {
            s1.support(new Trouble(1));
        }
    }
}
