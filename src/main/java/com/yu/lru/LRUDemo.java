package com.yu.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU： latest Recently used
 *
 * LinkedHashMap
 */
public class LRUDemo<K,V> extends LinkedHashMap<K,V> {

    private int capacity;

    /**
     * accessOrder：the ordering mode
     * <tt>true</tt> for access-order：会按最近使用排个序
     * <tt>false</tt> for insertion-order: 不会按最近使用排个序， 默认
     */
    public LRUDemo(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return super.size() > capacity;
    }

    public static void main(String[] args) {
        LRUDemo<String, String> lruDemo = new LRUDemo<>(3);
        lruDemo.put("1", "a");
        lruDemo.put("2", "b");
        lruDemo.put("3", "c");
        System.out.println(lruDemo.keySet());
        lruDemo.put("4", "d");
        System.out.println(lruDemo.keySet());
        lruDemo.put("2", "b");
        System.out.println(lruDemo.keySet());
        lruDemo.put("5", "b");
        System.out.println(lruDemo.keySet());

    }
}
