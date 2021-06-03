package com.juc.single;

/**
 * 静态内部类，也可以实现单例模式
 */
public class Holder {

    private Holder(){

    }

    public static Holder getInstance() {
        return InnerClass.HOLDER;
    }
    public static class InnerClass{
        private static final Holder HOLDER = new Holder();
    }
}
