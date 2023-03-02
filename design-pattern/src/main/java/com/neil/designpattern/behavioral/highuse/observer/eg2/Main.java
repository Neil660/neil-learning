package com.neil.designpattern.behavioral.highuse.observer.eg2;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/2/9 10:24
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        try {
            // 创建文件结构：/root /root/bin /root/bin.vi /root/bin/latex /root/tmp /root/usr
            System.out.println("Making root entries...");
            Directory rootdir = new Directory("root");
            Directory bindir = new Directory("bin");
            Directory tmpdir = new Directory("tmp");
            Directory usrdir = new Directory("usr");
            rootdir.add(bindir);
            rootdir.add(tmpdir);
            rootdir.add(usrdir);
            bindir.add(new File("vi", 10000));
            bindir.add(new File("latex", 20000));
            rootdir.accept(new ListVisitor());

            System.out.println();
            System.out.println("Making user entries...");
            Directory yuki = new Directory("yuki");
            Directory hanako = new Directory("hanako");
            Directory tomura = new Directory("tomura");
            usrdir.add(yuki);
            usrdir.add(hanako);
            usrdir.add(tomura);

            yuki.add(new File("diary.html", 100));
            yuki.add(new File("Composite.java", 200));
            hanako.add(new File("memo.txt", 300));
            tomura.add(new File("game.doc", 400));
            tomura.add(new File("junk.mail", 500));
            rootdir.accept(new ListVisitor());
        }
        catch (FileTreatementException e) {
            e.printStackTrace();
        }
    }
}
