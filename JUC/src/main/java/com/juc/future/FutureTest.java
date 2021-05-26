package com.juc.future;

import java.util.concurrent.*;

/**
 * java 实现异步调用
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        System.out.println("main函数开始执行");
        //自定义线程池
        ExecutorService service = new ThreadPoolExecutor(1, 1, 20L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
//        //1、使用Future实现异步调用
        Callable<String> callable = () -> {
            return "Future测试";
        };
        Future<String> future = service.submit(callable);
        //Future只能用get获取执行结果，会阻塞主线程，，如果不需要返回值时使用Future是可以的。
        System.out.println("执行结束，执行结果为:" + future.get());
//        System.out.println("main函数结束");
        //2、使用原生CompletableFuture实现异步调用

        //不指定线程池时使用forkJoinPool执行，默认创建的线程是守护线程
//        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(2L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("没有返回值的调用");
//        });
//        System.out.println("main函数结束");

        //指定线程池,不使用forkJoinPool
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println(10 / 0);
            return "调用成功";
        }, service);
        //当线程执行完时，执行下面的操作
        completableFuture.whenComplete((t, u) -> {
            System.out.println("t = " + t);//t是执行成功的正常的返回结果
            System.out.println("u = " + u);//u是执行失败的错误的返回结果
        }).exceptionally(//有异常发生时会执行下面的代码
                throwable -> {
            System.out.println(throwable.getMessage());
            return "执行失败";//可以通过get()方法获取return的结果
        });
        service.shutdown();
        System.out.println("main函数结束");

    }
}
