package com.juc.tools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅栏（加法计数器），一个线程组的线程需要等待所有线程完成任务后再继续执行下一次任务
 * 就像生活中我们会约朋友们到某个餐厅一起吃饭，有些朋友可能会早到，有些朋友可能会晚到，
 * 但是这个餐厅规定必须等到所有人到齐之后才会让我们进去。这里的朋友们就是各个线程，餐厅就是 CyclicBarrier。
 * 线程调用 await() 表示自己已经到达栅栏
 * BrokenBarrierException 表示栅栏已经被破坏，破坏的原因可能是其中一个线程 await() 时被中断或者超时
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Thread(() ->{
            System.out.println("五人全部到达，开始吃饭");
        }));
        for (int i = 1; i <= 5; i++) {
            final int t  = i;
            new Thread(()->{
                System.out.println("第" + t + "个人已到达");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
