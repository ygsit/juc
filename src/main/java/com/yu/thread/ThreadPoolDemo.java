package com.yu.thread;

import java.util.concurrent.*;

/**
 * 线程池：
 *  怎么配置：CPU线程数 + 1
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
//        initPool();

        //获取CPU线程数
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println(processors);

        //使用ThreadPoolExecutor创建线程池(推荐)
        ExecutorService executorService = new ThreadPoolExecutor(
                2, //常驻线程数
                processors + 1, //最大线程数,最优是配置cpu线程数+1
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3), //默认Integer.MAX_VALUE  21亿
                Executors.defaultThreadFactory(),
                //四大策略：默认AbortPolicy
                //new ThreadPoolExecutor.AbortPolicy()：超出抛异常
                //new ThreadPoolExecutor.CallerRunsPolicy()：超出回调，谁让来的找谁
                //new ThreadPoolExecutor.DiscardOldestPolicy()：抛弃队列中等待最久的任务
                //new ThreadPoolExecutor.DiscardPolicy()：默默地丢弃无法处理的任务，如果允许任务丢失，这是最好的一种策略。
                new ThreadPoolExecutor.AbortPolicy()
        );
        try {
            //10个顾客请求
            for (int i = 1; i <= 10; i++) {
                //Thread.sleep(200);
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //线程池最后需要释放资源
            executorService.shutdown();
        }

    }

    private static void initPool() {
        //        ExecutorService executorService = Executors.newFixedThreadPool(5);//一个银行网点，5个受理业务的窗口
//        ExecutorService executorService = Executors.newSingleThreadExecutor();//一个银行网点，1个受理业务的窗口
        ExecutorService executorService = Executors.newCachedThreadPool(); //一个银行网点，可扩展受理业务的窗口
        try {
            //10个顾客请求
            for (int i = 1; i <= 10; i++) {
                //Thread.sleep(200);
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //线程池最后需要释放资源
            executorService.shutdown();
        }
    }
}
