package com.multi.thread.example.case10;

import com.multi.thread.utils.Debug;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Decription 可能产生死锁的哲学家，使用显式锁，死锁后无法响应中断
 * @Author NEIL
 * @Date 2023/2/2 16:16
 * @Version 1.0
 */
public class BuggyLckBasedPhilosopher extends AbstractPhilosopher {
    public BuggyLckBasedPhilosopher(int id, Chopstick left, Chopstick right) {
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
        lock.lock();
        try {
            Debug.info("%s is picking up %s on his %s...%n", this, chopstick, chopstick == left ? "left" : "right");
            chopstick.pickUp();
        }
        catch (Exception e) {
            e.printStackTrace(); // 不大可能走到这里
            lock.unlock();
            return false;
        }
        return true;
    }
}
