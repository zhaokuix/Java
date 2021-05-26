package com.juc.pool;

import java.nio.file.WatchEvent;
import java.util.concurrent.*;

/**
 * Executors 三个方法、七个参数、四个拒绝策略
 */
public class Demo01 {

    public static void main(String[] args) {

        //单个线程
        ExecutorService service1 = Executors.newSingleThreadExecutor();
        //固定线程数的线程池
        ExecutorService service2 = Executors.newFixedThreadPool(5);
        //可伸缩的，遇强则强遇弱则弱，最大线程数是Integer.MAX_VALUE
        ExecutorService service3 = Executors.newCachedThreadPool();
        //自定义线程池，线程池能放入的最大线程数为：maximumPoolSize + BlockingQueue.size()，比如这个线程池最多能提交5+3=8个线程
        ExecutorService myService = new ThreadPoolExecutor(
                2,//核心线程数
                5,//最大线程数
                5L,//线程不使用时的存活时间
                TimeUnit.SECONDS,//存活时间的单位
                new LinkedBlockingQueue<>(3),//阻塞队列，自定义队列长度
                Executors.defaultThreadFactory(),//线程创建工厂
                new ThreadPoolExecutor.DiscardOldestPolicy()//拒绝策略
                //ThreadPoolExecutor.AbortPolicy//队列满了，还有人进来，不处理这个人，抛出异常
                //ThreadPoolExecutor.CallerRunsPolicy//哪来的去哪里
                //ThreadPoolExecutor.DiscardPolicy//队列满了，丢掉任务，不抛出异常
                //ThreadPoolExecutor.DiscardOldestPolicy//队列满了，尝试和最早的线程竞争，也不会抛出异常

        );
        CountDownLatch countDownLatch = new CountDownLatch(9);//设置初始值



        try {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 9; i++) {
                myService.submit(() -> {
                    System.out.println(Thread.currentThread().getName());
                    countDownLatch.countDown();
                });
            }
            countDownLatch.await();
            long end = System.currentTimeMillis();
            System.out.println(end - start + "ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //线程池用完必须关闭
            service1.shutdown();
            service2.shutdown();
            service3.shutdown();
            myService.shutdown();
        }

    }
}
