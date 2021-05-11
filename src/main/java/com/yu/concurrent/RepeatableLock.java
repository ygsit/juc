package com.yu.concurrent;

class Phone {

    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + "\t发短信");
        sendEmail();
    }

    public void sendEmail() {
        System.out.println(Thread.currentThread().getName() + "\t***发邮件");
    }
}


/**
 * 可重复锁：lock 和 synchronized 都是
 *  synchronized修饰的方法调用其他synchronized方法或者普通方法会自动加锁
 */
public class RepeatableLock {

    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(()->{
            phone.sendSMS();
        }, "A").start();

        new Thread(()->{
            phone.sendSMS();
        }, "B").start();
    }
}
