package com.neil.designpattern;

import org.checkerframework.checker.units.qual.K;
import org.junit.jupiter.api.Test;
import sun.nio.cs.ext.MacHebrew;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/2/18 0:07
 * @Version 1.0
 */
public class TestUtils {

    final static ThreadLocal<StringBuffer> threadLocal = new ThreadLocal<>();

    @Test
    public void t2() throws UnsupportedEncodingException {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        map.put(1, 2);
        map.get(1);

        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("1");
        list.get(0);

        Set<String> qpathSet = new LinkedHashSet<>();
        qpathSet.add("aaa");
        qpathSet.add("bbb");
        qpathSet.add("ccc");
        qpathSet.add("ddd");

        String[] commentsDubReverse = {"aaa", "bbb", "ccc"};
        String[] commentsDubs = {"1", "2", "3"};
        System.arraycopy(commentsDubReverse, 0, commentsDubs, 0, commentsDubs.length);


        System.out.println();
    }

    @Test
    public void leetcode() {
        Integer[] param = {1, 3, 4, 1, 2, 3, 1};
        Set<Integer> idxSet = new HashSet<>();
        List<Integer> r1 = new LinkedList<>();
        int m = 0;
        Map<Long, Long> map = new HashMap<>();
        map.put(2L, 3L);
        map.put(3L, 3L);
        System.out.println(map.toString());
        List<Long> ans = new ArrayList<>(m);
        Arrays.sort(param, (a, b) -> b - a);
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(5);
        root.right = new TreeNode(0);
        root.right.left = new TreeNode(4);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        System.out.println("2147483648".compareTo("2147483647"));
        System.out.println("2147483647".compareTo("2147483648"));
        System.out.println("-2147483648".compareTo("-2147483649"));
        System.out.println("-2147483649".compareTo("-2147483648"));
        myAtoi("3.14159");

    }

    public int myAtoi(String s) {
        s = s.trim();
        if ("".equals(s)) return 0;
        char c = s.charAt(0);
        boolean pn = true;
        int idx = 0;
        // 拿符号位
        if (c == '-' || c == '+') {
            pn = (c == '-') ? false : true;
            idx++;
            if (idx < s.length()) c = s.charAt(idx);
        }
        if (c < '0' || c > '9') return 0;

        // 找出有效的数字
        StringBuilder sb = new StringBuilder();
        int firstIdx = idx;
        while (idx < s.length()) {
            c = s.charAt(idx);
            if (idx == firstIdx && c == '0') {
                idx++;
                firstIdx = idx;
            } else if (c >= '0' && c <= '9') {
                sb.append(c);
                idx++;
            } else {
                break;
            }
        }
        String last = sb.toString().trim();
        if ("".equals(last)) return 0;
        String max = "2147483647";
        String min = "2147483648";
        int res = 0;
        if (pn) {
            if (bigger(last, max)) res = Integer.MAX_VALUE;
            else res = Integer.parseInt(last);
        } else {
            if (bigger(last, min)) res = Integer.MIN_VALUE;
            else res = -Integer.parseInt(last);
        }
        return res;
    }

    // f代表的整数如果大于s代表的整数，则返回true
    public boolean bigger(String f, String  s) {
        int fn = f.length();
        int sn = s.length();
        // 位数多，一定大
        if (fn > sn) return true;
            // 位数小，一定小
        else if (fn < sn) return false;
            // 位数一样时
        else {
            for (int i = 0; i < fn; i++) {
                char fc = f.charAt(i);
                char sc = s.charAt(i);
                if (fc - sc > 0) {
                    return true;
                } else if (fc - sc < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void test() {
        // list
        List<String> arrayList = new ArrayList<>();
        List<String> linkedList = new LinkedList<>();
        // set
        Set<String> hashSet = new HashSet<>();
        Set<String> treeSet = new TreeSet<>();
        // map
        Map<String, String> hashMap = new HashMap();
        Map<String, String> linkedHashMap = new LinkedHashMap<>();
        Map<String, String> treeMap = new TreeMap<>();
        Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();

        for (Entry<String, String> entry : treeMap.entrySet()) {
        }


        System.out.println("=========");
    }
}


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

