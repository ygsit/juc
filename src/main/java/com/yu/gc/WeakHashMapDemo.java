package com.yu.gc;

import java.util.WeakHashMap;

public class WeakHashMapDemo {
    public static void main(String[] args) {
        WeakHashMap<Integer, String> weekHashMap = new WeakHashMap<>();
        Integer key = 1;
        String value = "weekHashMap";
        weekHashMap.put(key, value);
        System.out.println(weekHashMap);
        key = null;
        System.out.println(weekHashMap);
        System.gc();
        System.out.println(weekHashMap);
    }
}
