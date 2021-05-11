package com.yu.collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;


public class NoSafeCollection {

    public static void main(String[] args) {
//        noSafeList();
//        noSafeSet();
        noSafeMap();
    }


    /**
     * ArrayList 线程不安全：
     * java.util.ConcurrentModificationException：并发修改异常，ArrayList在迭代的时候如果同时对其进行修改就会抛出
     * 方法：
     * 1、用Vector
     * 2、Collections.synchronizedList(new ArrayList<>())
     * 3、CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>()
     */
    private static void noSafeList() {
//        List<String> list = new ArrayList<>();
//        List<String> list = new Vector<>();
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }).start();
        }
    }

    /**
     * HashSet 线程不安全：
     * java.util.ConcurrentModificationException：并发修改异常，HashSet在迭代的时候如果同时对其进行修改就会抛出
     * 方法：
     * 1、Collections.synchronizedList(new ArrayList<>())
     * 2、CopyOnWriteArraySet<String> set = new CopyOnWriteArrayList<>()
     */
    private static void noSafeSet() {
//        Set<String> set = new HashSet<>();
//        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }).start();
        }
    }

    /**
     * HashMap 线程不安全：
     * java.util.ConcurrentModificationException：并发修改异常，HashMap在迭代的时候如果同时对其进行修改就会抛出
     * 方法：
     * 1、Collections.synchronizedMap(new HashMap<>())
     * 2、ConcurrentHashMap<String> map = new ConcurrentHashMap<>()
     */
    private static void noSafeMap() {
//        Map<String, Object> map = new HashMap<>();
//        Map<String, Object> map = Collections.synchronizedMap(new HashMap<>());
        Map<String, Object> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }).start();
        }
    }
}
