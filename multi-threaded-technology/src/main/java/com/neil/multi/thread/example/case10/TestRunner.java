package com.neil.multi.thread.example.case10;

import java.lang.reflect.Constructor;

/**
 * @Decription 哲学家就餐问题：n个哲学家坐在圆桌前，每个哲学家之间只有一根筷子，哲学家的重复动作是：先拿左边筷子然后拿右边筷子、吃饭、思考
 * @Author NEIL
 * @Date 2023/2/2 16:07
 * @Version 1.0
 */
public class TestRunner {
    public static void main(String[] args) throws Exception {
        // 创建并启动死锁检测与恢复线程
        new DeadlockDetector().start();

        // 哲学家数量
        int numOfPhilosopers = 2;
        // 创建筷子
        Chopstick[] chopsticks = new Chopstick[numOfPhilosopers];
        for (int i = 0; i < numOfPhilosopers; i++) {
            chopsticks[i] = new Chopstick(i);
        }

        String philosopherImplClassName = "RecoverablePhilosopher"; // RecoverablePhilosopher BuggyLckBasedPhilosopher
        for (int i = 0; i < numOfPhilosopers; i++) {
            // 通过反射创建哲学家
            Class<AbstractPhilosopher> philosopherClass = (Class<AbstractPhilosopher>) Class
                    .forName(TestRunner.class.getPackage().getName() + "."
                            + philosopherImplClassName);
            Constructor<AbstractPhilosopher> constructor = philosopherClass.getConstructor(
                    int.class, Chopstick.class, Chopstick.class);
            AbstractPhilosopher philosopher = constructor.newInstance(i, chopsticks[i], chopsticks[(i + 1) % numOfPhilosopers]);
            philosopher.start();
        }
    }
}
