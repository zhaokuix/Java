package com.juc.demo02;

/**
 * 生产者消费者问题 synchronized实现，notifyAll实现线程唤醒，wait实现线程休眠
 * 虚假唤醒：
 * 当一个条件满足时，很多线程都被唤醒了，但是只有其中部分是有用的唤醒，其它的唤醒都是无用功
 * 比如说买货，如果商品本来没有货物，突然进了一件商品，这是所有的线程都被唤醒了，但是只能一个人买，所以其他人都是假唤醒，获取不到对象的锁
 */
public class ProducerConsumer {
    public static void main(String[] args) {
        Data data = new Data();
        //生产者线程
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.produce();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "生产者01").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.produce();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"生产者02").start();
        //消费者线程
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.consume();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"消费者01").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data.consume();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"消费者02").start();
    }
}

//资源类
class Data{
    private Integer num = 0;
    //生产
    public synchronized void produce() throws InterruptedException {
        //此处必须用while，不能用if，if会导致虚假唤醒
        //因为if只会执行一次，执行完会接着向下执行if（）外边的
        //而while不会，直到条件满足才会向下执行while（）外边的
        while (num != 0){
            this.wait();
        }
        num++;
        System.out.println(Thread.currentThread().getName() + "生产=>" + num);
        this.notifyAll();
    }
    //消费
    public synchronized void consume() throws InterruptedException {
        while (num <= 0){
            this.wait();
        }
        num--;
        System.out.println(Thread.currentThread().getName() + "消费=>" + num);
        this.notifyAll();
    }
}
