package com.neil.designpattern.creational.highuse.singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * @Decription 静态内部类：本质是借助JVM的类加载机制保证实例的唯一性
 * @Author Huang Chengyi
 * @Date 2022/8/2 11:32
 * @Version 1.0
 */
public class InnerClassSingleton implements Serializable {
    private static class InnerClassHolder {
        private static InnerClassSingleton instance = new InnerClassSingleton();
    }

    private InnerClassSingleton() {
        // 防止通过反射的方式破坏单例
        if (InnerClassHolder.instance != null) {
            throw new RuntimeException("单例不允许多个实例");
        }
    }

    public static InnerClassSingleton getInstance() {
        return InnerClassHolder.instance;
    }

    // 通过序列化和反序列化依旧保持单例
    Object readResolve() throws ObjectStreamException {
        return InnerClassHolder.instance;
    }
}
