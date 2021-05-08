package demo2;

public class ThreadStop implements Runnable{
    boolean flag = true;
    int i = 0;
    @Override
    public void run() {
        while (flag){
            System.out.println("线程正在运行" + i++);
        }
    }
    public void stop(){
        this.flag = false;
    }

    public static void main(String[] args) {
        ThreadStop threadStop = new ThreadStop();
        new Thread(threadStop).start();
        for (int i = 0; i < 500; i++) {
            //停止线程
            if (i == 490){
                System.out.println("线程停止" + i);
                threadStop.stop();
            }
            System.out.println("输出++++" + i);
        }
    }
}
