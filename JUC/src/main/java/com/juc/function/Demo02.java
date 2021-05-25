package com.juc.function;

import java.util.function.Predicate;

/**
 * 断定型接口,有一个输入，返回布尔值
 */
public class Demo02 {
    public static void main(String[] args) {
        //断定型接口测试，判断一个字符串是否为空
//        Predicate<String> predicate = new Predicate<String>() {
//            @Override
//            public boolean test(String o) {
//                return o != null && o.isEmpty();
//            }
//        };
//        lambda简化
        Predicate<String> predicate = (o) -> {return o != null && o.isEmpty();};
        System.out.println(predicate.test(""));

    }
}
