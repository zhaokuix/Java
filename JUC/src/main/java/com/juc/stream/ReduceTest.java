package com.juc.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

public class ReduceTest {
    public static void main(String[] args) {
//        test1();//345ms
//        test2();//228ms
//        test3();//160ms
        test4();
    }
    public static void test1(){
        //计算十亿以内的加法
        long start = System.currentTimeMillis();
        long sum = 0;
        for (long i = 0; i <= 10_0000_0000; i++) {
            sum += i;
        }
        long end = System.currentTimeMillis();
        System.out.println(sum);
        System.out.println(end - start + "ms");
    }

    public static void test2(){
        //计算十亿以内的加法
        long start = System.currentTimeMillis();
        Long sum = LongStream.rangeClosed(0L,10_0000_0000L).parallel().reduce((a,b) -> a+b).getAsLong();
        long end = System.currentTimeMillis();
        System.out.println(sum);
        System.out.println(end - start + "ms");
    }

    public static void test3(){
        //计算十亿以内的加法
        long start = System.currentTimeMillis();
        //identity设置为0L，表示底数是0L，计算十亿次即0+0+1+2+...+10_0000_0000
        //identity设置为1L，表示底数是1L，计算十亿次即1+0+1+2+...+10_0000_0000
        Long sum = LongStream.rangeClosed(0L,10_0000_0000L).parallel().reduce(0L,(a,b) -> a+b);
        long end = System.currentTimeMillis();
        System.out.println(sum);
        System.out.println(end - start + "ms");
    }

    public static void test4(){
        List<Integer> list = Arrays.asList(1,2,3,4);
        int result = list.stream().parallel().reduce(1, (a,b) -> a+b, (a, b) -> a+b-1);
        System.out.println(result);
        result = list.stream().reduce(1, (a,b) -> a+b);
        System.out.println(result);
//        result = list.stream().reduce((a,b) -> a+b).get();
//        System.out.println(result);
//        list = Arrays.asList(Integer.MAX_VALUE,Integer.MAX_VALUE);
//        long ans = list .stream().reduce(0L,(a,b) ->  a + b, (a,b)-> null );
//        System.out.println(ans);
    }
}
