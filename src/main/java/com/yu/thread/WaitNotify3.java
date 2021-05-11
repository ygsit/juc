package com.yu.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 在新API下，可以用lock代替synchronize，
 * 用condition.await() 代替 wait()
 * 用condition.signal() () 代替 notify()
 * 用condition.signalAll() () () 代替 notifyAll()
 */
class Operator3 {
    private int num = 0;

    private final Lock lock = new ReentrantLock();
    //获取condition对象
    private final Condition condition = lock.newCondition();

    public void increment() throws InterruptedException {
        lock.lock();
        try {
            while (num != 0) {
                //等待
                condition.await();
            }
            num ++ ;
            System.out.println(Thread.currentThread().getName() + "\t\t" + num);
            //通知
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws InterruptedException {
        lock.lock();
        try {
            while (num == 0) {
                //等待
                condition.await();
            }
            num -- ;
            System.out.println(Thread.currentThread().getName() + "\t\t" + num);
            //通知
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 四个线程，两个加一，两个减一，来十轮最后为0
 */
public class WaitNotify3 {
    public static void main(String[] args) {
        Operator3 operator = new Operator3();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    operator.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    operator.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    operator.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    operator.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();
    }
}
