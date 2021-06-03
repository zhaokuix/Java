package com.juc.volatilet;

import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Volatile
 * 可以保证可见性
 * 不保证原子性
 * 禁止指令重排序
 */
public class VolatileTest {

    //不加volatile,会进入死循环
//    private static int num = 1;
    //加volatile可以保证可见性，不保证原子性
    private volatile static int num = 1;
//    使用原子类可以保证原子性,原理采用compareAndSwap方案，即CAS指令完成，相较于加锁效率要高的多
//    在并发环境下，某个线程对共享变量先进行操作，如果没有其他线程争用共享数据那操作就成功；
//    如果存在数据的争用冲突，那就才去补偿措施，比如不断的重试机制，直到成功为止，
//    因为这种乐观的并发策略不需要把线程挂起，也就把这种同步操作称为非阻塞同步（操作和冲突检测具备原子性）。
//    在硬件指令集的发展驱动下，使得 "操作和冲突检测" 这种看起来需要多次操作的行为只需要一条处理器指令便可以完成，
//    这些指令中就包括非常著名的CAS指令（Compare-And-Swap比较并交换）

    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) {
//        test1();
        //test2()计算结果小于等于100，说明volatile不保证原子性
//        test2();
        test3();
    }


    public static void test1(){

        new Thread(()->{
            while (num == 1){

            }
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2L);//确保线程都起来了
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            num++;
            System.out.println("num:" + num);
        }).start();

    }

    public static void test2(){

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                num++;
            }).start();
        }
        System.out.println(num);
    }

    public static void test3(){

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                atomicInteger.addAndGet(1);
            }).start();
        }
        System.out.println(atomicInteger);
    }

}

