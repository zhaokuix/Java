package demo1;

/**
 * 继承 thread类，重写run方法，调用start开启线程
 * 注意：线程开启不一定立即执行，由cpu调度执行
 */
public class TestThread extends Thread{

    @Override
    public void run() {
        for (int i = 0; i < 200; i++) {
            System.out.println("我在看代码-----" + i);
        }
    }

    public static void main(String[] args) {
        new TestThread().start();
        for (int i = 0; i < 2000; i++) {
            System.out.println("我在学习多线程-----" + i);
        }
    }
}
