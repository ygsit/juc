package com.yu.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程通信问题3：使用阻塞队列
 *
 * 生产一个消费一个，五秒后叫停
 */


class MyResources3{
    //定义标识
    private volatile boolean flag = true; //true表示一直生产消费，false代表停止

    private BlockingQueue<String> blockingQueue;

    //定义原子引用
    AtomicInteger atomicInteger = new AtomicInteger();

    public MyResources3(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void product(BlockingQueue<String> blockingQueue) throws InterruptedException {
        while (flag) {
            System.out.println(Thread.currentThread().getName() + "\t开始生产");
            boolean b = blockingQueue.offer(atomicInteger.incrementAndGet() + "", 2l, TimeUnit.SECONDS);
            if(b){
                System.out.println(Thread.currentThread().getName() + "\t生产成功" + atomicInteger.get());
            } else {
                System.out.println(Thread.currentThread().getName() + "\t生产失败");
            }
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        System.out.println(Thread.currentThread().getName() + "线程被叫停");
    }

    public void consumer(BlockingQueue<String> blockingQueue) throws InterruptedException {
        while (flag) {
            System.out.println(Thread.currentThread().getName() + "\t开始消费");
            String poll = blockingQueue.poll(2l, TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getName() + "\t消费了" + poll);
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        System.out.println(Thread.currentThread().getName() + "线程被叫停");
    }

    public void clear(){
        //停止生产
        this.flag = false;
    }

}

public class ProductConsumer3 {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);
        MyResources3 myResources3 = new MyResources3(blockingQueue);
        new Thread(()->{
            try {
                myResources3.product(blockingQueue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        new Thread(()->{
            try {
                myResources3.consumer(blockingQueue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();

        try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
        myResources3.clear();
    }
}
