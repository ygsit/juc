package com.yu.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch主要有两个方法,
 * 当一个或多个线程调用await方法时,调用线程会被阻塞.
 * 其他线程调用countDown方法计数器减1(调用countDown方法时线程不会阻塞),
 * 当计数器的值变为0,因调用await方法被阻塞的线程会被唤醒,继续执行
 * <p>
 * <p>
 * CountDownLatch：当前面线程全完成后再执行
 * countDown()：方法计数器减1
 * await()：阻塞等待
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "国灭");
                //减一
                countDownLatch.countDown();
            }, CountryEnum.countryEnumForeach(i).getCountryName()).start();
        }
        //等待
        countDownLatch.await();
        System.out.println("***秦国一统天下");
    }

    private static void closeDoor() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t离开教室");
                //减一
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        //等待
        countDownLatch.await();
        System.out.println("关门");
    }
}
