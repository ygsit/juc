package com.yu.concurrent;

/**
 * 死锁：
 * 产生死锁的四个必要条件：
 * 1、互斥条件：一个资源每次只能被一个进程使用
 * 2、请求与保持条件：一个进程因请求资源而阻塞时，对已获得的资源保持不放
 * 3、不剥夺条件：进程已获得的资源，在未使用完之前，不能强行剥夺
 * 4、循环等待条件：若干进程之间形成一种头尾相接的循环等待资源关系
 */

class DeadThread implements Runnable {
    private String lockA;
    private String lockB;

    public DeadThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }


    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t自己持有锁" + lockA + "\t尝试获得" + lockB);
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t自己持有锁" + lockB + "\t尝试获得" + lockA);
            }
        }
    }
}

public class DeadLockDemo {
    public static void main(String[] args) {
        DeadThread deadThreadA = new DeadThread("lockA", "lockB");
        DeadThread deadThreadB = new DeadThread("lockB", "lockA");
        new Thread(deadThreadA, "A").start();
        new Thread(deadThreadB, "B").start();
    }
}
