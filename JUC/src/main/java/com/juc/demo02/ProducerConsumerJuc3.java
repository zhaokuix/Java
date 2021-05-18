package com.juc.demo02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  * 使用condition实现顺序唤醒，使多线程下实现顺序执行
 */
public class ProducerConsumerJuc3 {
    public static void main(String[] args) {
        Data4 data = new Data4();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.A();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAAAAAA").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.B();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBBBBBB").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.C();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "CCCCCCC").start();

    }
}

//资源类
class Data4{
    private Integer num = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition conditionA = lock.newCondition();
    private final Condition conditionB = lock.newCondition();
    private final Condition conditionC = lock.newCondition();
    public void A() throws InterruptedException {
        //此处必须用while，不能用if，if会导致虚假唤醒
        //因为if只会执行一次，执行完会接着向下执行if（）外边的
        //而while不会，直到条件满足才会向下执行while（）外边的
        lock.lock();
        try {
            while (num != 0){
                conditionA.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName() + "当前num为" + num);
            conditionB.signal();//唤醒B
        } finally {
            lock.unlock();
        }
    }
    public void B() throws InterruptedException {
        lock.lock();
        try {
            while (num != 1){
                conditionB.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName() + "当前num为" + num);
            conditionC.signal();//唤醒C
        } finally {
            lock.unlock();
        }
    }
    public void C() throws InterruptedException {
        lock.lock();
        try {
            while (num != 2){
                conditionC.await();
            }
            num = 0;
            System.out.println(Thread.currentThread().getName() + "当前num为" + num);
            conditionA.signal();
        } finally {
            lock.unlock();
        }
    }
}
