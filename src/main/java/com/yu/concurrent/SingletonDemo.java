package com.yu.concurrent;

/**
 * 单例模式
 */
public class SingletonDemo {

    //使用volatile防止指令重排
    private static volatile SingletonDemo singletonDemo = null;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "\t初始化对象");
    }

    //DCL双端检锁机制 (指令重排对象初始化可能会出问题)
    public static SingletonDemo getInstance() {
        if (singletonDemo == null) {
            synchronized (SingletonDemo.class) {
                if (singletonDemo == null) {
                    singletonDemo = new SingletonDemo();
                }
            }
        }
        return singletonDemo;
    }

    public static void main(String[] args) {

        for (int i = 1; i <= 10; i++) {

            new Thread(() -> {
                SingletonDemo.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
