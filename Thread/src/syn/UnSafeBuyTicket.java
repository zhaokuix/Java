package syn;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程不安全
 */
public class UnSafeBuyTicket {


    public static void main(String[] args) {
        BuyTicket buyTicket = new BuyTicket();
        new Thread(buyTicket, "学生").start();
        new Thread(buyTicket, "老师").start();
        new Thread(buyTicket, "黄牛").start();
    }

}
class BuyTicket implements Runnable{
    private Integer tickets = 10;
    private boolean flag = true;
    //定义可重入锁
    private final static ReentrantLock lock = new ReentrantLock();
    @Override
    public void run() {
        while (flag){
            try {
                buy();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //同步方法，锁的是this
//    private synchronized void  buy() throws InterruptedException {
    private void  buy() throws InterruptedException {
        //锁变化的对象
//        synchronized (tickets){
//            //判断是否有票
//            if (tickets <= 0){
//                flag = false;
//                return;
//            }
//            System.out.println(Thread.currentThread().getName() + "拿到" + tickets--);
//        }
        //使用lock实现锁

        try {
            lock.lock();
            //判断是否有票
            if (tickets <= 0){
                flag = false;
                return;
            }
            System.out.println(Thread.currentThread().getName() + "拿到" + tickets--);
        } finally {
            lock.unlock();
        }
        //模拟延时
        Thread.sleep(300);
    }
}
