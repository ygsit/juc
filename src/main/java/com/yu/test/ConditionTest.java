package com.yu.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PrintNum {
    public int num = 1;

    Lock lock = new ReentrantLock();
    Condition conditionA = lock.newCondition();
    Condition conditionB = lock.newCondition();
    Condition conditionC = lock.newCondition();

    public void printA() {
        lock.lock();
        try {
            while (num % 3 == 0 || num % 3 == 2) {
                conditionA.await();
            }
            System.out.println(Thread.currentThread().getName() + "输出：" + num);
            num++;
            conditionB.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            while (num % 3 == 0 || num % 3 == 1) {
                conditionB.await();
            }
            System.out.println(Thread.currentThread().getName() + "输出：" + num);
            num++;
            conditionC.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            while (num % 3 == 1 || num % 3 == 2) {
                conditionC.await();
            }
            System.out.println(Thread.currentThread().getName() + "输出：" + num);
            num++;
            conditionA.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}

public class ConditionTest {
    public static void main(String[] args) {
        PrintNum printNum = new PrintNum();
        int target = 100;
        new Thread(() -> {
            for (int i = 0; i < target / 3; i++) {
                printNum.printA();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < target / 3; i++) {
                printNum.printB();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < target / 3; i++) {
                printNum.printC();
            }
        }, "C").start();

    }
}
