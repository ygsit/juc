package com.yu.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class ChangeVa {
    public volatile int num = 0;

    public void change() {
        this.num = 10;
    }

    //不保证原子性
    public void addPlus() {
        this.num++;
    }

    //解决volatile不保证原子性，AtomicInteger就可以保证
    AtomicInteger atomicInteger = new AtomicInteger();
    public void atomicAdd() {
        atomicInteger.getAndIncrement(); //i++
    }
}

/**
 * volatile保证可见性
 * <p>
 * synchronized保证可见性，但是当CPU被一直占用的时候，同步就会出现不及时，也就出现了后台线程一直不结束的情况。
 * <p>
 * <p>
 * volatile不保证原子性
 */
public class VolatileDemo {
    public static void main(String[] args) {
//        initVisible();

        //volatile不保证原子性
        ChangeVa changeVa = new ChangeVa();
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    changeVa.addPlus();
                    changeVa.atomicAdd();
                }
            }, String.valueOf(i)).start();
        }

        //Thread.activeCount():获取当前执行的线程数，默认有main线程和gc线程
        while (Thread.activeCount() > 2) {
            Thread.yield();//礼让
        }
        System.out.println(Thread.currentThread().getName() + " type int， finally num = " + changeVa.num);
        System.out.println(Thread.currentThread().getName() + " type atomicInteger， finally num = " + changeVa.atomicInteger);
    }

    //volatile保证可见性
    private static void initVisible() {
        ChangeVa changeVa = new ChangeVa();
        new Thread(() -> {
            System.out.println("进入了A线程");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            changeVa.change();
            System.out.println(Thread.currentThread().getName() + " update num value，num = " + changeVa.num);
        }, "A").start();

        while (changeVa.num == 0) {

        }
        System.out.println(Thread.currentThread().getName() + " get num value，num = " + changeVa.num);
    }
}
