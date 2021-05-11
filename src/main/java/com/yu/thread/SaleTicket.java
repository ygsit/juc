package com.yu.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//  线程     操作     资源类
class Ticket {
    private int num = 300;

    private final Lock lock = new ReentrantLock();

    public void saleTicket() {
        lock.lock();
        try {
            if (num > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第" + num + "张票，还剩" + --num + "张票");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


public class SaleTicket {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    ticket.saleTicket();
                }
            }
        }, "A").start();
        new Thread((() -> {
            for (int i = 0; i < 400; i++) {
                ticket.saleTicket();
            }
        }), "B").start();
        new Thread((() -> {
            for (int i = 0; i < 400; i++) {
                ticket.saleTicket();
            }
        }), "C").start();
    }
}
