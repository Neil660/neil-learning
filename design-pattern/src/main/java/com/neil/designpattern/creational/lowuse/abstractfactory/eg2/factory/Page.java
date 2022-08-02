package com.neil.designpattern.creational.lowuse.abstractfactory.eg2.factory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * @Decription 抽象的产品：抽象表示HTML的类
 * @Author Huang Chengyi
 * @Date 2022/8/2 14:10
 * @Version 1.0
 */
public abstract class Page {
    protected String titile;
    protected String author;
    protected ArrayList content = new ArrayList();

    public Page(String title, String author) {
        this.titile = title;
        this.author = author;
    }

    public void add(Item item) {
        content.add(item);
    }

    public void output() {
        try {
            String filename = titile + ".html";
            Writer writer = new FileWriter(filename);
            writer.write(this.makeHTML());
            writer.close();
            System.out.println(filename + "编写完成");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract String makeHTML();
}
