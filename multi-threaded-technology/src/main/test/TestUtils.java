import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/12/31 15:04
 * @Version 1.0
 */
public class TestUtils {
    public volatile int value = 1;
    public int get() {
        return value;
    }
    @Test
    public void test2() {
        AtomicInteger ai = new AtomicInteger(1);
        ai.compareAndSet(1, 3);
        boolean ready = false;

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        lock.lock();
        try {
            if (ready) {
                condition.signalAll();
            }
        }
        finally {
            lock.unlock();
        }
    }

    public String intToRoman(int num) {
        StringBuilder res = new StringBuilder();
        int[] dic = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] dicLuoma = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        for (int i = 0; i < dic.length;) {
            if (num >= dic[i]) {
                num -= dic[i];
                res.append(dicLuoma[i]);
            } else {
                i++;
            }
        }
        return res.toString();
    }

    @Test
    public void test1() {
        ByteBuffer buf = ByteBuffer.allocate(10);
        buf.put((byte) 3);
        buf.put((byte) 1);
        buf.put((byte) 't');

        /*// get后position加1 put也会加1
        byte b = buf.get();
        // 设置当前的读取指针为初始位置
        buf.position(0);
        // 当前数据实际个数
        buf.limit(3);
        // 获取到第一个数据
        b = buf.get();*/

        // position=3,limit=10 ===> position=0,limit=3，存储的数据跟数据顺序没变
        buf.flip();
        System.out.println();
    }
}
