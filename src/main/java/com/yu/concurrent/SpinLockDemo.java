package com.yu.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自定义自旋锁
 */
public class SpinLockDemo {

    //定义一个线程时间戳原子引用
    AtomicReference<Thread> atomicReference = new AtomicReference<>(null);

    public void myLock() {
        //获取当前线程
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t*****come in myLock");
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    public void myUnLock() {
        //获取当前线程
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t invoked myUnlock");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(()->{
            spinLockDemo.myLock();
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            spinLockDemo.myUnLock();
        }, "A").start();

        new Thread(()->{
            //保证上面线程先执行
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
            spinLockDemo.myLock();
            spinLockDemo.myUnLock();
        }, "B").start();
    }

}
