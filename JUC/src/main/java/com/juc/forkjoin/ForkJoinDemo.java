package com.juc.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * ForkJoin大数求和
 */
public class ForkJoinDemo extends RecursiveTask<Long> {
    //数据量超过此值进入forkJoin
    private final static Long FLAG = 3330L;
    private final Long start;
    private final Long end;

    public ForkJoinDemo(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    /**
     * 计算方法
     * @return 返回和
     */
    @Override
    protected Long compute() {
        long sum = 0L;
        if (end - start < FLAG){
            for (Long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }else { //forkJoin递归
            long middle = (end + start) / 2;
            ForkJoinDemo task1 = new ForkJoinDemo(start, middle);
            //拆分任务，把任务压入线程队列
            task1.fork();
            ForkJoinDemo task2 = new ForkJoinDemo(middle + 1, end);
            //拆分任务，把任务压入线程队列
            task2.fork();

            return task1.join() + task2.join();
        }
    }
}
