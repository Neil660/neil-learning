package com.multi.thread.example.case1;

import com.multi.thread.annotation.Actor;
import com.multi.thread.annotation.ConcurrencyTest;
import com.multi.thread.annotation.Expect;
import com.multi.thread.annotation.Observer;

/**
 * @Decription 再现JIT指令重排序的Demo
 * @Author NEIL
 * @Date 2022/12/10 14:10
 * @Version 1.0
 */
@ConcurrencyTest(iterations = 200000)
public class JITReorderingDemo {
    private int externalData = 1;
    private Helper helper;

    @Actor
    public void createHelper() {
        // 分为三个指令执行，但是②和③的顺序可能会发生指令重排
        // ①分配Helper实例所需的内存空间，并获得一个指向该空间的引用objRef
        // ②调用Helper类的构造器初始化objRef引用指向的Helper实例
        // ③将Helper实例引用objRef赋值给实例变量helper
        helper = new Helper(externalData);
    }

    @Observer({
            @Expect(desc = "Helper is null", expected = -1),
            @Expect(desc = "Helper is not null,but it is not initialized", expected = 0),
            @Expect(desc = "Only 1 field of Helper instance was initialized", expected = 1),
            @Expect(desc = "Only 2 fields of Helper instance were initialized", expected = 2),
            @Expect(desc = "Only 3 fields of Helper instance were initialized", expected = 3),
            @Expect(desc = "Helper instance was fully initialized", expected = 4)})
    public int consume() {
        int sum = 0;

        /*
         * 由于我们未对共享变量helper进行任何处理（比如采用volatile关键字修饰该变量），
         * 因此，这里可能存在可见性问题，即当前线程读取到的变量值可能为null。
         */
        final Helper observedHelper = helper;
        if (null == observedHelper) {
            sum = -1;
        }
        else {
            sum = observedHelper.payloadA + observedHelper.payloadB + observedHelper.payloadC + observedHelper.payloadD;
        }
        return sum;
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        // 调用测试工具运行测试代码
        TestRunner.runTest(JITReorderingDemo.class);
    }

    static class Helper {
        int payloadA;
        int payloadB;
        int payloadC;
        int payloadD;

        public Helper(int externalData) {
            this.payloadA = externalData;
            this.payloadB = externalData;
            this.payloadC = externalData;
            this.payloadD = externalData;
        }

        @Override
        public String toString() {
            return "Helper [" + payloadA + ", " + payloadB + ", " + payloadC + ", "
                    + payloadD + "]";
        }

    }
}
