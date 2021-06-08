package com.juc.rwl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁 ReadWriteLock，细粒度地控制锁，提高效率
 * Lock readLock()//读锁（共享锁），读取时所有线程都可以读，锁是防止读到写的中间值。
 * Lock writeLock()//写锁（独占锁），写入时只能有一个线程写入
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        //写入线程
        for (int i = 0; i < 5; i++) {
            final int t = i;
            new Thread(() -> {
                myCache.put(t+"",t+"");
            }, String.valueOf(i)).start();
        }
        //读取线程
        for (int i = 0; i < 5; i++) {
            final int t = i;
            new Thread(() -> {
                myCache.get(t+"");
            },String.valueOf(i)).start();
        }
    }
}
class MyCache{
    
    private final Map<String, String> map = new HashMap<>();

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put(String key, String value){
        //写锁
        readWriteLock.writeLock().lock();
        try {
            System.out.println("开始写入" + key);
            map.put(key, value);
            System.out.println("写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get(String key){
        readWriteLock.readLock().lock();
        try {
            System.out.println("开始读取" + key);
            map.get(key);
            System.out.println("读取完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}
