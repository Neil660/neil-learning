package com.neil.designpattern.structural.lowuse.flyweight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Decription 角色--轻量级工厂
 * @Author Huang Chengyi
 * @Date 2022/8/4 15:21
 * @Version 1.0
 */
public class TreeFactory {
    private static Map<String, Tree> map = new ConcurrentHashMap<>();

    public static Tree getTree(String name, String data) {
        if (map.containsKey(name)) {
            return map.get(name);
        }
        Tree tree = new Tree(name, data);
        map.put(name, tree);
        return tree;
    }
}
