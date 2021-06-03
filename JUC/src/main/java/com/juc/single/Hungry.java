package com.juc.single;

/**
 * 饿汉式单例,缺点浪费空间
 */
public class Hungry {

    private Hungry(){
    }

    private final static  Hungry HUNGRY = new Hungry();
    public static Hungry getInstance(){
        return HUNGRY;
    }
}
