package com.neil.multi.thread.example.case10;

/**
 * @Decription 一根筷子对象
 * @Author NEIL
 * @Date 2023/2/2 15:57
 * @Version 1.0
 */
public class Chopstick {
    private int id;
    private int status;

    public Chopstick(int id) {
        super();
        this.id = id;
    }

    /**
     * 拿起筷子，代表占用了资源
     */
    public void pickUp() {
        status = 1;
    }

    /**
     * 放下筷子，代表释放了资源
     */
    public void putDown() {
        status = 0;
    }

    @Override
    public String toString() {
        return id + "号筷子";
    }
}
