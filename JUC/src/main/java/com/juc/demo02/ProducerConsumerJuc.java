package com.juc.demo02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者问题 Lock和Condition实现
 */
public class ProducerConsumerJuc {
    public static void main(String[] args) {
        Data2 data = new Data2();
        //生产者线程
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.produce();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "生产者01").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.produce();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"生产者02").start();
        //消费者线程
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.consume();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"消费者01").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.consume();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"消费者02").start();
    }
}

//资源类
class Data2{
    private Integer num = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    //生产
    public void produce() throws InterruptedException {
        //此处必须用while，不能用if，if会导致虚假唤醒
        //因为if只会执行一次，执行完会接着向下执行if（）外边的
        //而while不会，直到条件满足才会向下执行while（）外边的
        lock.lock();
        try {
            while (num != 0){
                condition.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName() + "生产=>" + num);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
    //消费
    public void consume() throws InterruptedException {
        lock.lock();
        try {
            while (num <= 0){
                condition.await();
            }
            num--;
            System.out.println(Thread.currentThread().getName() + "消费=>" + num);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
