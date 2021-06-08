package com.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SpinLockDemo {

    public static void main(String[] args) throws InterruptedException {

        Lock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
        SpinLock spinLock = new SpinLock();

        new Thread(()->{
            spinLock.lock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                spinLock.unLock();
            }
        },"A").start();

        TimeUnit.SECONDS.sleep(1);
        new Thread(()->{
            spinLock.lock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                spinLock.unLock();
            }
        },"B").start();

    }
}
