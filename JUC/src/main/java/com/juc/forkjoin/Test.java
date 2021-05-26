package com.juc.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * 计算十亿以内的加法
 */
public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
        test2();
        test3();
    }

    /**
     * 普通加法
     */
    public static void test1(){
        long start = System.currentTimeMillis();
        long sum = 0;
        for (long i = 0; i <= 20_0000_0000L; i++) {
            sum += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("test1: " + sum + " 执行时间: " + (end - start) + "ms");
    }

    /**
     * ForkJoin
     */
    public static void test2() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinDemo(0L,20_0000_0000L);
        ForkJoinTask<Long> result = forkJoinPool.submit(task);
        long sum = result.get();
        long end = System.currentTimeMillis();
        System.out.println("test2: " + sum + " 执行时间: " + (end - start) + "ms");
    }

    /**
     * Stream Reduce
     */
    public static void test3() {
        long start = System.currentTimeMillis();
        long sum = LongStream.rangeClosed(0L,20_0000_0000L).parallel().reduce(0, Long::sum);
        long end = System.currentTimeMillis();
        System.out.println("test3: " + sum + " 执行时间: " + (end - start) + "ms");
    }
}
