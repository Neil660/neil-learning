import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/5/10 10:54
 * @Version 1.0
 */
public class TestUtils2 {
    @Test
    public void test1() {
        TestUtils tu = new TestUtils();
        AtomicIntegerFieldUpdater<TestUtils> aifu = AtomicIntegerFieldUpdater.newUpdater(TestUtils.class, "value");
        aifu.getAndSet(tu, 3);



    }
}
