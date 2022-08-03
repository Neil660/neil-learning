package com.neil.designpattern.creational.highuse.singleton;

/**
 * @Decription 饿汉单例模式：类加载的初始化阶段就完成了实例的初始化，本质是借助JVM的类加载机制保证实例的唯一性
 * @Author Huang Chengyi
 * @Date 2022/8/2 11:30
 * @Version 1.0
 */
public class HungrySingleton {
    private static HungrySingleton instance = new HungrySingleton();

    private HungrySingleton() {
        // 防止通过反射的方式破坏单例
        if (HungrySingleton.instance != null) {
            throw new RuntimeException("单例不允许多个实例");
        }
    }

    public static HungrySingleton getInstance() {
        return instance;
    }
}
