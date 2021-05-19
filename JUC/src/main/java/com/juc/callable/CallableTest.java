package com.juc.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //传统线程执行方式，没有返回值，不抛出异常
        new Thread(new ThreadOld()).start();
        /**
         * Callable执行方式，既要有返回值又要抛出异常
         * 因为Thread运行只接收Runnable接口或者是其实现类，
         * 必须使用Runnable的一个实现类封装一下才能运行，-----FutureTask类
         * Callable返回结果会放在FutureTask中
         */
        FutureTask<String> futureTask = new FutureTask<>(new ThreadCall());
        new Thread(futureTask, "A").start();
        new Thread(futureTask, "B").start();//futureTask执行完一次后会缓存数据，不会被重复调用
        //get方法可能会产生阻塞，如果线程执行是一个耗时的操作
        //要把他放在最后执行，或者使用异步通信来处理
        String ans = futureTask.get();
        System.out.println(ans);
    }
}

class ThreadCall implements Callable<String> {

    @Override
    public String call() {
        System.out.println("call()");
        return "1024";
    }
}

class ThreadOld implements Runnable {
    @Override
    public void run() {
        System.out.println("run()");
    }
}