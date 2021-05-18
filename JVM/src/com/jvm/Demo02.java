package com.jvm;

import java.util.ArrayList;
import java.util.Random;

public class Demo02 {
    //输出dump文件 -XX:+HeapDumpOnOutOfMemoryError
    //Jprofiler分析流程，先分析大对象，确认对象是哪个线程的，找到对应线程，查看报错行数
    byte[] array = new byte[1*1024*1024];//1MB
    public static void main(String[] args) {
        ArrayList<Demo02> list = new ArrayList<>();
        int count = 0;
        //OOM样例
        try {
            while (true){
                list.add(new Demo02());//问题所在
                count++;
            }
        } catch (Exception e) {
            System.out.println("count=" + count);
            e.printStackTrace();
        }
    }
}
