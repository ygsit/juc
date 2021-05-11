package com.yu.thread;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class MyTask extends RecursiveTask<Integer> {

    private static final int ADJUST_VALUE = 10;

    private Integer begin;
    private Integer end;
    private int result;

    public MyTask(Integer begin, Integer end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if ((end - begin) <= ADJUST_VALUE) {
            for (int i = begin; i <= end; i++) {
                result = result + i;
            }
        } else {
            int middle = (begin + end) / 2;
            MyTask myTask1 = new MyTask(begin, middle);
            MyTask myTask2 = new MyTask(middle + 1, end);
            myTask1.fork();
            myTask2.fork();
            result = myTask1.join() + myTask2.join();
        }
        return result;
    }
}


/**
 * 分支合并：
 *  Fork：把一个复杂任务进行分拆，大事化小
 *  Join：把分拆任务的结果进行合并
 */
public class ForkJoinDemo {

    public static void main(String[] args) throws Exception {
        //创建资源类，计算1-100
        MyTask myTask = new MyTask(1, 100);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> submit = forkJoinPool.submit(myTask);

        //获取结果
        System.out.println(submit.get());

        //分支合并线程池
        forkJoinPool.shutdown();

    }
}
