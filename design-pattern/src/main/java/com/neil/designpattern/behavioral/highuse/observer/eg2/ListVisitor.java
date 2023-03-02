package com.neil.designpattern.behavioral.highuse.observer.eg2;

import java.util.Iterator;

/**
 * @Decription Visitor子类，显示文件和文件夹一览
 * @Author NEIL
 * @Date 2023/2/9 10:23
 * @Version 1.0
 */
public class ListVisitor extends Visitor {
    private String currentDir = "";
    @Override
    public void visit(File file) {
        System.out.println(currentDir + "/" + file);
    }

    @Override
    public void visit(Directory directory) {
        System.out.println(currentDir + "/" + directory);
        String savedir = currentDir;
        currentDir = currentDir + "/" + directory.getName();
        Iterator iterator = directory.iterator();
        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            entry.accept(this);
        }
        currentDir = savedir;
    }
}
