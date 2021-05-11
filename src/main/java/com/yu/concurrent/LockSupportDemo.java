package com.yu.concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport：底层原理是Unsafe.class中的方法
 * API:
 * LockSupport.park()：阻塞
 * LockSupport.unpark(Thread t)：解锁
 *
 * 说明：
 *  1、unpark可以在park之前使用，且不会在park使用后造成阻塞
 *  2、unpark相当于发放通行证，且不可累积，最多只有一个。当使用两个或多个park的时候会造成阻塞
 */
public class LockSupportDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "====进入阻塞====");
            //调用LockSupport.park方法阻塞
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "----接触阻塞后----");
        }, "A");
        t1.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "给A线程通行证");
            //调用LockSupport.unpark方法给A线程接触阻塞
            LockSupport.unpark(t1);
        }, "B");
        t2.start();
    }
}
