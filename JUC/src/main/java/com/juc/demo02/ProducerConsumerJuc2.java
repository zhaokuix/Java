package com.juc.demo02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用两个lock分别锁定、condition实现精准唤醒，生产者1生产的只能由消费者1消费，生产者2生产的只能由消费者2消费
 */
public class ProducerConsumerJuc2 {
    public static void main(String[] args) {
        Data3 data = new Data3();
        //生产者线程
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.produce1();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "生产者01").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.produce2();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"生产者02").start();
        //消费者线程
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.consume1();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"消费者01").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.consume2();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"消费者02").start();
    }
}

//资源类
class Data3{
    private Integer num = 0;
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();
    private final Condition p1 = lock1.newCondition();
    private final Condition p2 = lock1.newCondition();
    private final Condition c1 = lock2.newCondition();
    private final Condition c2 = lock2.newCondition();
    //生产
    public void produce1() throws InterruptedException {
        produce(lock1, p1, c1);
    }
    public void produce2() throws InterruptedException {
        produce(lock2, p2, c2);
    }

    private void produce(Lock lock2, Condition p2, Condition c2) throws InterruptedException {
        lock2.lock();
        try {
            //此处必须用while，不能用if，if会导致虚假唤醒
            //因为if只会执行一次，执行完会接着向下执行if（）外边的
            //而while不会，直到条件满足才会向下执行while（）外边的
            while (num != 0){
                p2.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName() + "生产=>" + num);
            c2.signal();
        } finally {
            lock2.unlock();
        }
    }

    //消费
    public void consume1() throws InterruptedException {
        consume(lock1, c1, p1);
    }

    public void consume2() throws InterruptedException {
        consume(lock2, c2, p2);
    }

    private void consume(Lock lock1, Condition c1, Condition p1) throws InterruptedException {
        lock1.lock();
        try {
            while (num <= 0){
                c1.await();
            }
            num--;
            System.out.println(Thread.currentThread().getName() + "消费=>" + num);
            p1.signal();
        } finally {
            lock1.unlock();
        }
    }
}
