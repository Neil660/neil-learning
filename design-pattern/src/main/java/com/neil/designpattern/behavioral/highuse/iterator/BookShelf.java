package com.neil.designpattern.behavioral.highuse.iterator;

/**
 * @Decription 书架。角色--具体的集合
 * @Author Huang Chengyi
 * @Date 2022/8/4 14:51
 * @Version 1.0
 */
public class BookShelf implements Aggregate {
    private Book[] books;

    private int last = 0;

    public BookShelf(int max) {
        this.books = new Book[max];
    }

    public Book getBookAt(int index) {
        return books[index];
    }

    public void appendBook(Book book) {
        this.books[last] = book;
        last++;
    }

    public int getLength() {
        return last;
    }

    @Override
    public Iterator iterator() {
        return new BookShelfIterator(this);
    }
}
