package com.neil.multi.thread.example.case10;

import com.neil.multi.thread.utils.Debug;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Decription 可能产生死锁的哲学家，使用显式锁，支持死锁恢复
 * @Author NEIL
 * @Date 2023/2/2 16:11
 * @Version 1.0
 */
public class RecoverablePhilosopher extends AbstractPhilosopher {
    public RecoverablePhilosopher(int id, Chopstick left, Chopstick right) {
        super(id, left, right);
    }

    /**
     * 拿起筷子
     * @param chopstick
     * @return
     */
    @Override
    protected boolean pickUpChopstick(Chopstick chopstick) {
        final ReentrantLock lock = LOCK_MAP.get(chopstick);
        try {
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            // 使当前线程释放其已持有的锁
            Debug.info("%s detected interrupt.", Thread.currentThread().getName());
            // 放下现在拿在手里的筷子
            Chopstick theOtherChopstick = chopstick == left ? right : left;
            theOtherChopstick.putDown();
            // 释放锁
            LOCK_MAP.get(theOtherChopstick).unlock();
            return false;
        }

        try {
            Debug.info("%s is picking up %s on his %s...%n", this, chopstick, chopstick == left ? "left" : "right");
            chopstick.pickUp();
        } catch (Exception e) {
            // 不大可能走到这里
            e.printStackTrace();
            lock.unlock();
            return false;
        }
        return true;
    }
}
