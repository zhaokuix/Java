package com.juc.unsafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 多线程修改异常 java.util.ConcurrentModificationException
 */
public class ListTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
//        List<String> list = new Vector<>();
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
//        List<String> list = new CopyOnWriteArrayList<>();

        /**
         * ArrayList线程不安全，解决方案：
         * 1、直接给该对象加锁synchronized (list)
         * 2、使用Vector，给每个操作使用synchronized加锁
         * 3、手动给ArrayList加synchronized锁 Collections.synchronizedList
         * 4、使用读写分离的list CopyOnWriteArrayList
         * 多线程调用时，读取是固定的，写入时，先加锁，然后复制一份原数据，添加新数据，最后写回数据，释放锁
         * CopyOnWriteArrayList写入数据时才加锁
         */
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(Thread.currentThread().getName() + list.toString());
            }).start();
        }
    }
}
