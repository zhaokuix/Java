package com.juc.queue;

import java.util.concurrent.SynchronousQueue;

/**
 * 没有容量
 * 进入一个元素，必须等待取出来后才能再往里放一个元素
 * put()、take()
 */
public class SynchronousQueueTest {
    public static void main(String[] args) {
        SynchronousQueue<String> queue = new SynchronousQueue<>();

        //put线程
        for (int i = 0; i < 10; i++) {
            final int t = i;
            new Thread(() -> {
                try {
                    queue.put("" + t);
//                    System.out.println(t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        //take线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
