package com.neil.designpattern.creational.important.singleton;

/**
 * @Decription 懒汉单例模式：延迟加载，只有在真正使用的时候才会实例化。
 * @Author Huang Chengyi
 * @Date 2022/8/2 11:30
 * @Version 1.0
 */
public class LazySingleton {
    /*
     *  volatile保证instance的操作不会发生指令的重排序
     *  线程1进到了instance = new LazySingleton()的时候，可能由于指令重排，instance进行了赋值但是还没初始化；
     *  此时线程2进来了第一个check，发现instance!=null，直接返回instance，但是其实它还没初始化，就会报错。
     */
    private volatile static LazySingleton instance;

    private LazySingleton() {
        // 防止通过反射的方式破坏单例
        if (LazySingleton.instance != null) {
            throw new RuntimeException("单例不允许多个实例");
        }
    }

    // synchronized避免多线程环境下单例被破坏
    public static LazySingleton getInstance() {
        // double check
        if (instance == null) {
            synchronized(LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
