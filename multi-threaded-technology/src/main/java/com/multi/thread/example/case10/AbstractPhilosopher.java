package com.multi.thread.example.case10;

import com.multi.thread.utils.Debug;
import com.multi.thread.utils.Tools;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Decription 哲学家抽象
 * @Author NEIL
 * @Date 2023/2/2 16:03
 * @Version 1.0
 */
public abstract class AbstractPhilosopher extends Thread {
    protected final int id;
    protected final Chopstick left;
    protected final Chopstick right;

    /**
     * 为确保每个Chopstick实例有且仅有一个显式锁（而不重复创建）与之对应,<br>
     * 这里的map必须采用static修饰!
     */
    protected final static ConcurrentMap<Chopstick, ReentrantLock> LOCK_MAP;
    static {
        LOCK_MAP = new ConcurrentHashMap<>();
    }

    public AbstractPhilosopher(int id, Chopstick left, Chopstick right) {
        super(id + "号哲学家");
        this.id = id;
        this.left = left;
        this.right = right;
        // 每个筷子对应一个(唯一)锁实例
        LOCK_MAP.putIfAbsent(left, new ReentrantLock());
        LOCK_MAP.putIfAbsent(right, new ReentrantLock());
    }

    @Override
    public void run() {
        for (; ; ) {
            think();
            eat();
        }
    }

    public void eat() {
        // 先后拿起左手边和右手边的筷子
        if (pickUpChopstick(left) && pickUpChopstick(right)) {
            // 同时拿起两根筷子的时候才能够吃饭
            try {
                doEat();
            }
            finally {
                // 放下筷子
                putDownChopsticks(right, left);
            }
        }
    }

    protected abstract boolean pickUpChopstick(Chopstick chopstick);

    /**
     * 放下筷子
     * @param chopstick1
     * @param chopstick2
     */
    protected void putDownChopsticks(Chopstick chopstick1, Chopstick chopstick2) {
        try {
            putDownChopstick(chopstick1);
        }
        finally {
            putDownChopstick(chopstick2);
        }
    }
    protected void putDownChopstick(Chopstick chopstick) {
        final ReentrantLock lock = LOCK_MAP.get(chopstick);
        try {
            Debug.info("%s is putting down %s on his %s...%n", this, chopstick, chopstick == left ? "left" : "right");
            chopstick.putDown();
        }
        finally {
            lock.unlock();
        }
    }

    protected void doEat() {
        Debug.info("%s is eating...%n", this);
        Tools.randomPause(10);
    }

    public void think() {
        Debug.info("%s is thinking...%n", this);
        Tools.randomPause(10);
    }

    @Override
    public String toString() {
        return id + "号哲学家";
    }
}
