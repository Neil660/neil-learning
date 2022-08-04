package com.neil.designpattern.behavioral.highuse.iterator;

/**
 * @Decription 遍历书架的类。角色--具体的迭代器
 * @Author Huang Chengyi
 * @Date 2022/8/4 14:51
 * @Version 1.0
 */
public class BookShelfIterator implements Iterator {
    private BookShelf bookShelf;

    private int index;

    public BookShelfIterator(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        if (index < bookShelf.getLength()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object next() {
        Book book = bookShelf.getBookAt(index);
        index++;
        return book;
    }
}
