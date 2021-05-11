package com.yu.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程通信问题2：指定线程生产和消费：多几把钥匙
 * lock             await           signal
 * <p>
 * A线程打印5次，B线程打印10次，C线程打印15次，来10轮
 */
class MySources2 {

    private String num = "A";//A B C 表示

    //创建Lock
    Lock lock = new ReentrantLock();
    Condition conditionA = lock.newCondition();
    Condition conditionB = lock.newCondition();
    Condition conditionC = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            while (!"A".equals(num)) {
                conditionA.await();
            }
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "打印" + i + "次");
            }
            this.num = "B";
            conditionB.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try {
            while (!"B".equals(num)) {
                conditionB.await();
            }
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "打印" + i + "次");
            }
            this.num = "C";
            conditionC.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try {
            while (!"C".equals(num)) {
                conditionC.await();
            }
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "打印" + i + "次");
            }
            this.num = "A";
            conditionA.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class ProductConsumer2 {
    public static void main(String[] args) {
        MySources2 mySources2 = new MySources2();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                mySources2.print5();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                mySources2.print10();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                mySources2.print15();
            }
        }, "C").start();
    }
}
