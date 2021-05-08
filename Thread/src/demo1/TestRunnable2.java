package demo1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多个线程操作同一个资源，数据紊乱
 */
public class TestRunnable2 implements Runnable{
    int tickets;
    TestRunnable2(int tickets){
        this.tickets = tickets;
    }
    @Override
    public void run() {
        while (true){
            if (tickets <= 0){
                break;
            }
            System.out.println(Thread.currentThread().getName() + "拿到了第" + tickets-- +"张票====>");
            //模拟延时
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

    }
}
