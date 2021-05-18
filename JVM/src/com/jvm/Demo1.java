package com.jvm;

public class Demo1 {
    public static void main(String[] args) {
        //返回jvm虚拟机试图使用的最大内存,默认情况下是电脑内存的1/4
        long max = Runtime.getRuntime().maxMemory(); //字节Byte
        //返回jvm的初始总内存，默认情况下是电脑内存的1/64
        long total = Runtime.getRuntime().totalMemory();
        System.out.println("max=" + max + "字节\t" + (max/1024/1024) + "MB");
        System.out.println("total=" + total + "字节\t" + (total/1024/1024) + "MB");
    }
    /*
    出现OOM怎么办？
    1、尝试扩大堆内存看结果。2、可以使用内存分析工具分析内存，看哪里出现了问题
    -Xmx1024m:设置jvm能使用的最大内存为1024m
    -Xms200m:设置jvm的初始总内存为200m
    -Xmn50m:设置年轻代大小为50m
    -Xss1024KK:设置每个线程的堆栈大小
    -XX:+PrintGCDetails 输出内存详细信息
    -XX:+HeapDumpOnOutOfMemoryError 遇到内存溢出时输出内存快照
     */
    /*
max=3817865216字节	3641MB
total=1029177344字节	981MB
Heap
 PSYoungGen      total 305664K, used 26214K [0x000000076ab00000, 0x0000000780000000, 0x00000007c0000000)    298.5MB
  eden space 262144K, 10% used [0x000000076ab00000,0x000000076c499b50,0x000000077ab00000)
  from space 43520K, 0% used [0x000000077d580000,0x000000077d580000,0x0000000780000000)
  to   space 43520K, 0% used [0x000000077ab00000,0x000000077ab00000,0x000000077d580000)
 ParOldGen       total 699392K, used 0K [0x00000006c0000000, 0x00000006eab00000, 0x000000076ab00000)    683MB
  object space 699392K, 0% used [0x00000006c0000000,0x00000006c0000000,0x00000006eab00000)
 Metaspace       used 3329K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 355K, capacity 388K, committed 512K, reserved 1048576K

可以看出，新生代+老年代的内存之和为981.5M，由此可见元空间貌似不占内存，
因为元空间被放在了主内存中，不受jvm参数限制
     */
}
