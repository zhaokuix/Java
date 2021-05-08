package demo1;

public class TestRunnable implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 200; i++) {
            System.out.println("我在看代码-----" + i);
        }
    }

    public static void main(String[] args) {
        new Thread(new TestRunnable()).start();
        for (int i = 0; i < 2000; i++) {
            System.out.println("我在学习多线程-----" + i);
        }
    }
}
