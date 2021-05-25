package com.juc.function;

import java.util.function.Function;

/**
 * Function函数型接口，有一个输入，有一个输出
 */
public class Demo01 {
    public static void main(String[] args) {
        //输入一个字符串，返回输出+1
//        Function<String, String> function = new Function<String, String>() {
//            @Override
//            public String apply(String o) {
//                return o+1;
//            }
//        };
        //函数式接口lambda简化
        Function<String, String> function = (o) ->{return o+1;};
        System.out.println(function.apply("0"));


    }
}
