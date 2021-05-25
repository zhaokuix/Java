package com.juc.function;

import java.util.function.Consumer;

/**
 * 消费型接口，只有输入没有输出
 */
public class Demo03 {
    public static void main(String[] args) {
//        Consumer<String> consumer =  new Consumer<String>() {
//            @Override
//            public void accept(String o) {
//                System.out.println(o);
//            }
//        };
        //lambda简化
        Consumer<String> consumer  = (o) -> {System.out.println(o); };
        consumer.accept("666");
    }
}
