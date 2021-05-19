package com.juc.tools;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *　Semaphore也是一个线程同步的辅助类，可以维护当前访问自身的线程个数，并提供了同步机制。
 * 简而言之就是限流的作用
 * 举例抢车位，三个车位、六辆车
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        //最大允许三个线程获取到执行信号
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    //semaphore.acquire(); 获取到可执行的信号,如果已经满了，等到被释放为止
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢到车位");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName() + "离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    //semaphore.release();执行完毕释放信号，唤醒等待的线程
                    semaphore.release();
                }
            }).start();
        }
    }
}
