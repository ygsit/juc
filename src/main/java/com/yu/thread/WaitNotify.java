package com.yu.thread;

//wait只是等待，但线程没有停止，当唤醒之后还会继续工作
//void notify() 唤醒在此对象监视器上等待的单个线程。
//void notifyAll() 唤醒在此对象监视器上等待的所有线程
class Operator {
    private int num = 0;

    public synchronized void increment() throws InterruptedException {
        if (num != 0) {
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
        if (num == 0) {
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
 * 两个线程，一个加一，一个减一，来十轮最后为0
 */
public class WaitNotify {
    public static void main(String[] args) {
        Operator operator = new Operator();
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
    }
}
