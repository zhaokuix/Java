package com.juc.unsafe;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

//ConcurrentModificationException 多线程修改异常
public class SetTest {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        /**
         * HashSet线程不安全
         * 1、使用synchronized (set)给该对象加锁
         * 2、使用Collections.synchronizedSet()方法，强制加上synchronized
         * 3、使用CopyOnWriteArraySet,读写分离，写的时候加synchronized
         */

//        Set<String> set = Collections.synchronizedSet(new HashSet<>());
//        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                synchronized (set){
                    set.add(UUID.randomUUID().toString().substring(0,5));
                    System.out.println(set);
                }
            }).start();
        }
    }
}
