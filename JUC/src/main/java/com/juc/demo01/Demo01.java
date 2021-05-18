package com.juc.demo01;

public class Demo01 {
    public static void main(String[] args) {
        //获取cpu核心数
        //cpu密集型、IO密集型
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
