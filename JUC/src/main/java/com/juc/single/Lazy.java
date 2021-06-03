package com.juc.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 饿汉式单例
 */
public class Lazy {
    private Lazy(){
        System.out.println(Thread.currentThread().getName());
    }
//    private static Lazy LAZY;

    //单线程下没问题
    public static Lazy getInstance1(){
        if (LAZY == null){
            LAZY = new Lazy();
        }
        return LAZY;
    }

    private volatile static Lazy LAZY;

    //双重检测锁模式的懒汉式单例，简称DCL懒汉式单例
    public static Lazy getInstance2(){
        if (LAZY == null){//第一重检测
            synchronized (Lazy.class){
                if (LAZY == null){
                    LAZY = new Lazy();//new不是一个原子性操作，可能会发生指令重排序
                    /*
                    1、分配内存空间
                    2、执行构造方法，初始化对象
                    3、把这个对象的变量指向这个空间

                    如果执行顺序为1、2、3，那么没有问题
                    如果执行顺序是1、3、2，两个线程A、B，如果A先把LAZY指向了该内存空间，此时LAZY不是null，
                    B在这个时候调用getInstance，因为LAZY不是null，所以直接返回了，此时对象还没有完成初始化是错误的
                    要解决此问题需要volatile来禁止指令重排序
                    但是即使使用volatile也是不安全的，因为可以使用反射破坏单例模式，可以使用enum来实现单例模式
                     */
                }
            }
        }
        return LAZY;
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        for (int i = 0; i < 10; i++) {
//            new Thread(Lazy::getInstance2).start();
//        }
        //反射破坏单例模式
        Constructor<Lazy> declaredConstructor = Lazy.class.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        Lazy lazy1 = declaredConstructor.newInstance();
        Lazy lazy2 = declaredConstructor.newInstance();
        System.out.println(lazy1);
        System.out.println(lazy2);
    }

}
