package com.yu.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程通信问题1：生产一个消费一个：注意多线程的虚假唤醒
 * synchronized     wait            notify
 * lock             await           signal
 */
class MySources {

    private int num = 0;//0表示生产，1表示消费

    //创建Lock
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void prod() {
        lock.lock();
        try {
            while (num != 0) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + "生产者生产");
            num ++;
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void cons() {
        lock.lock();
        try {
            while (num == 0) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + "*****消费者消费");
            num --;
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


public class ProductConsumer {

    public static void main(String[] args) {
        MySources mySources = new MySources();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                mySources.prod();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mySources.cons();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                mySources.prod();
            }
        }, "C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mySources.cons();
            }
        }, "D").start();
    }
}
