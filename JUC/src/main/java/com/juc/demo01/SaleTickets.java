package com.juc.demo01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//卖票 模拟并发
public class SaleTickets {
    public static void main(String[] args) {
        SaleTicket saleTicket = new SaleTicket();
        new Thread(saleTicket).start();
        new Thread(saleTicket).start();
        new Thread(saleTicket).start();
    }
}

class SaleTicket implements Runnable{
    Ticket ticket = new Ticket();
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            ticket.sale();
        }
    }
}

class Ticket{
    private int num = 10;
    //可重入锁
    private final Lock lock = new ReentrantLock();
    public void sale(){
        try{
            lock.lock();
            if (num > 0 ){
                System.out.println("线程" + Thread.currentThread().getName() + "卖出第" + num + "张票, 剩余" + --num + "张票");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}
