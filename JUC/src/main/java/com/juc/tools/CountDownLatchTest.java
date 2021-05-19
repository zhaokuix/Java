package com.juc.tools;

import java.util.concurrent.CountDownLatch;

/**
 * 线程减法计数器 CountDownLatch
 * 原理，每次有线程调用countDown()方法时，数量-1，
 * 计数器变为0时，当前线程结束await()，继续执行
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);//设置初始值
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "has gone");
                countDownLatch.countDown();//数量减一
            }).start();
        }
        //等待计数器归零再向下执行
        countDownLatch.await();
        System.out.println("close the door");
    }
}
