package com.neil.designpattern.behavioral.highuse.iterator;

/**
 * @Decription
 * @Author Huang Chengyi
 * @Date 2022/8/4 14:48
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        //将书发到书架上，并将书的名字按顺序显示出来
        BookShelf bookShelf = new BookShelf(4);
        bookShelf.appendBook(new Book("b1"));
        bookShelf.appendBook(new Book("b2"));
        bookShelf.appendBook(new Book("b3"));
        bookShelf.appendBook(new Book("b4"));
        Iterator iterator = bookShelf.iterator();
        while (iterator.hasNext()) {
            Book book = (Book) iterator.next();
            System.out.println(book.getName());
        }
    }
}
