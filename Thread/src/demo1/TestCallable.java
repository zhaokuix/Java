package demo1;

import java.util.concurrent.*;

/**
 * Callable可以定义返回值，可以抛出异常
 *
 */
public class TestCallable implements Callable<Boolean> {

    int tickets;
    TestCallable(int tickets){
        this.tickets = tickets;
    }
    @Override
    public Boolean call() {
        while (true){
            if (tickets <= 0){
                break;
            }
            System.out.println(Thread.currentThread().getName() + "拿到了第" + tickets-- +"张票====>");
        }
        return true;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TestCallable t1 = new TestCallable(5);
        TestCallable t2 = new TestCallable(5);
        TestCallable t3 = new TestCallable(5);
        //创建执行服务
        ExecutorService service = Executors.newFixedThreadPool(3);
        //提交执行
        Future<Boolean> f1 = service.submit(t1);
        Future<Boolean> f2 = service.submit(t2);
        Future<Boolean> f3 = service.submit(t3);
        //获取结果
        System.out.println(f1.get() + " " + f2.get() + " " + f3.get());
        //关闭服务
        service.shutdownNow();
    }
}
