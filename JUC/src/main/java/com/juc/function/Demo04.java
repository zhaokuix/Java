package com.juc.function;

import java.util.function.Supplier;

/**
 * 供给型接口，只有输出没有输入
 */
public class Demo04 {
    public static void main(String[] args) {
//        Supplier<String> supplier =  new Supplier<String>() {
//            @Override
//            public String get() {
//                return "666";
//            }
//        };
        //lambda简化
        Supplier<String> supplier = () -> {return "666";};
        System.out.println(supplier);
    }
}
