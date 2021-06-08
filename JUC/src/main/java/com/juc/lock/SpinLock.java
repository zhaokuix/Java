package com.juc.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自定义自旋锁
 */
public class SpinLock {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();
    //加锁
    public void lock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "  myLock");
        //如果线程是空的，才能设置为thread，否则锁住，自旋锁
        while (!atomicReference.compareAndSet(null,thread)){}
    }

    //解锁
    public void unLock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "  myUnLock");
        //如果线程不空，置为null，解锁
        atomicReference.compareAndSet(thread,null);
    }

}
