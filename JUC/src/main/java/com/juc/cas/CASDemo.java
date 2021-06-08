package com.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * CAS：CompareAndSet,比较并交换，CAS是一条CPU指令
 */
public class CASDemo {

    public static void main(String[] args) {
 /*       AtomicInteger atomicInteger = new AtomicInteger(2020);
        //public final boolean compareAndSet(int expect, int update) {
        //期望、更新，如果我的期望值达到了就更新，否则不更新
        System.out.println(atomicInteger.compareAndSet(2020, 2021));//更新成功
        System.out.println(atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(2020, 2022));//更新失败
        System.out.println(atomicInteger.get());
*/
//        CAS会有ABA问题，使用原子引用解决

//        Integer使用超过-128~127的范围的数，会修改失败，因为超过这个范围在加1时会new一个Integer，与原地址不相同，无法修改成功，这是一个大坑
//        对于Integer包装类，使用-128~127之间的数才能进行测试
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1,1);
        new Thread(() ->{
            int stamp = atomicStampedReference.getStamp();//获得版本号
            System.out.println("a1=>" + stamp);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(1, 2, 1, stamp + 1));
            System.out.println("a2=>" + atomicStampedReference.getStamp());

            System.out.println(atomicStampedReference.compareAndSet(2, 1, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("a3=>" + atomicStampedReference.getStamp());
            },"A").start();
        new Thread(() ->{
            int stamp = atomicStampedReference.getStamp();//获得版本号
            System.out.println("b1=>" + stamp);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(1, 3, 1, stamp + 1));
            System.out.println("b2=>" + atomicStampedReference.getStamp());

        },"B").start();
    }
}
