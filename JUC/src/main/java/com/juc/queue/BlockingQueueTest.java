package com.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueTest {

    public static void main(String[] args) throws InterruptedException {
//        test01();
//        test02();
        test03();
    }

    /**
     * add/remove方法超过长度/没数据会抛出异常，element方法检测队首元素，没有队首元素会抛出异常
     */
    public static void test01(){
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);
        blockingQueue.add("1");
        blockingQueue.add("2");
        blockingQueue.add("3");
        System.out.println(blockingQueue.element());
//        blockingQueue.add("4");
        blockingQueue.remove();
        blockingQueue.remove();
        blockingQueue.remove();
        System.out.println(blockingQueue.element());
//        blockingQueue.remove();
        System.out.println(blockingQueue);
    }

    /**
     * offer/poll方法超过长度/没数据不会抛出异常，会返回false/null
     * offer/poll也可以设置一个阻塞超时时间，超过该时间还是没法处理再返回false/null
     */
    public static void test02() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);
        blockingQueue.offer("1");
        blockingQueue.offer("2");
        blockingQueue.offer("3");
        System.out.println(blockingQueue.offer("4"));
        System.out.println(blockingQueue.offer("4", 2L, TimeUnit.SECONDS));
        blockingQueue.poll();
        blockingQueue.poll();
        blockingQueue.poll();
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
//        System.out.println(blockingQueue);
    }

    /**
     * put/take方法队列满/空时一直阻塞等待不返回状态，不抛出异常
     */
    public static void test03() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);
        blockingQueue.put("1");
        blockingQueue.put("2");
        blockingQueue.put("3");
        blockingQueue.put("4");
        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
//        System.out.println(blockingQueue);
    }
}
