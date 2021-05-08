package demo3;

/**
 * 生产者消费者模型---管程法
 * 生产者、消费者、产品、缓冲区
 */
public class TestPC {
    public static void main(String[] args) {
        SynContainer container = new SynContainer();
        new Thread(new Producer(container)).start();
        new Thread(new Consumer(container)).start();
    }

}

/**
 * 生产者
 */
class Producer implements Runnable{
    private SynContainer synContainer;

    public Producer(SynContainer synContainer) {
        this.synContainer = synContainer;
    }

    @Override
    public void run() {
        //生产100个产品
        for (int i = 1; i <= 100; i++) {
            System.out.println("生产第" + i + "个产品");
            synContainer.push(new Chicken(i));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
/**
 * 消费者
 */
class Consumer implements Runnable{
    private SynContainer synContainer;

    public Consumer(SynContainer synContainer) {
        this.synContainer = synContainer;
    }

    @Override
    public void run() {
        //消费100个产品
        for (int i = 0; i < 100; i++) {
            Chicken chicken = synContainer.pop();
            System.out.println("消费了第" + chicken.getId() + "个产品");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
/**
 * 产品
 */
class Chicken{
    private int id;

    public Chicken(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
/**
 * 缓冲区
 */
class SynContainer{
    //定义缓冲区
    private final Chicken[] chickens = new Chicken[10];
    //产品计数器
    private int count = 0;
    //生产者生产产品
    public synchronized void push(Chicken chicken){
        //如果容器没有满，生产者生产
        if (count == chickens.length){
            //如果容器满了，等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        chickens[count] = chicken;
        count++;
        this.notifyAll();
    }

    //消费者消费产品
    public synchronized Chicken pop(){

        if (count == 0){
            //如果容器里没产品了，等待生产者生产
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count--;
        Chicken chicken = chickens[count];
        this.notifyAll();
        return chicken;
    }
}