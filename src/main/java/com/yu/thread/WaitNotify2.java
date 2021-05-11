package com.yu.thread;

/**
 * 总结：
 *  1、高内聚低耦合前提下，线程操作资源类
 *  2、线程间通信，判断/干活/通知
 *  3、多线程交互中，必须要防止多线程的虚假唤醒，也即(判断只用while，不能用if)
 */
class Operator2 {
    private int num = 0;

    public synchronized void increment() throws InterruptedException {
        while (num != 0) {
            //等待
            wait();
        }
        //干活
        num++;
        System.out.println(Thread.currentThread().getName() + "线程进行加一，num = " + num);
        //通知消费
        notifyAll();
    }

    public synchronized void decrement() throws InterruptedException {
        while (num == 0) {
            //等待
            wait();
        }
        //干活
        num--;
        System.out.println(Thread.currentThread().getName() + "线程进行减一，num = " + num);
        //通知消费
        notifyAll();
    }
}

/**
 * 四个线程，两个加一，两个减一，来十轮最后为0
 */
public class WaitNotify2 {
    public static void main(String[] args) {
        Operator2 operator = new Operator2();
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
