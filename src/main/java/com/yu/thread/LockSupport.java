package com.yu.thread;

/**
 * 唤醒指定线程
 * LockSupport.park(); LockSupport的park可以让当前线程进入wait状态
 * LockSupport.unpark(t2); 解除指定线程的wait状态
 * interrupt：停止线程
 */
public class LockSupport {
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(()->{
            System.out.println("t1:start");
            java.util.concurrent.locks.LockSupport.park();
            System.out.println("t1:continue");
        },"t1");
        Thread t2=new Thread(()->{
            System.out.println("t2:start");
            java.util.concurrent.locks.LockSupport.park();
            System.out.println("t2:continue");
        },"t2");
        Thread t3=new Thread(()->{
            System.out.println("t3:start");
            java.util.concurrent.locks.LockSupport.park();
            System.out.println("t3:continue");
        },"t3");
        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(5000);
        java.util.concurrent.locks.LockSupport.unpark(t2);
        t1.interrupt();
        t3.interrupt();
    }
}