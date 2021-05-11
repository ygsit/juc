package com.yu.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A打印五次，B打印10次，C打印15次，
 * 接着A打印五次，B打印10次，C打印15次，
 * 循环10次
 * <p>
 * 精准唤醒
 */
public class AwaitSignal {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print5();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print10();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print15();
            }
        }, "C").start();
    }
}


class ShareResource {
    //定义标志位，1表示A，2表示B，3表示C
    private int num = 1;
    private final Lock lock = new ReentrantLock();
    //定义三把钥匙
    private final Condition conditionA = lock.newCondition();
    private final Condition conditionB = lock.newCondition();
    private final Condition conditionC = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            if (num != 1) {
                //等待
                conditionA.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "打印了：" + (i + 1) + "次");
            }
            //修改标志位
            num = 2;
            //通知B
            conditionB.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try {
            if (num != 2) {
                //等待
                conditionB.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "打印了：" + (i + 1) + "次");
            }
            //修改标志位
            num = 3;
            //通知C
            conditionC.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try {
            if (num != 3) {
                //等待
                conditionC.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "打印了：" + (i + 1) + "次");
            }
            //修改标志位
            num = 1;
            //通知B
            conditionA.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}