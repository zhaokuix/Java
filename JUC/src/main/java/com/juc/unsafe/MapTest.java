package com.juc.unsafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//ConcurrentModificationException
public class MapTest {

    public static void main(String[] args) {
//        Map<String, String> map = new HashMap<>(16, 0.75f);
//        Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
        Map<String, String> map = new ConcurrentHashMap<>();
        /**
         * HashMap线程不安全，解决方案
         * 1、使用synchronized给对象加锁
         * 2、使用Collections.synchronizedMap()加锁
         * 3、使用ConcurrentHashMap
         */
        /**
         * HashMap底层实现原理
         * 1、初始容量：默认初始容量为16。
         *   加载因子：默认加载因子为0.75，当数量超过  目前容量*0.75时，会自动扩容
         * ConcurrentHashMap底层实现原理
         */

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,5));
                System.out.println(map);
            }).start();
        }
    }
}
