package demo3;

/**
 * 生产者消费者模型---信号灯法
 */
public class TestTv {
    public static void main(String[] args) {
        Tv tv = new Tv();
        new Thread(new Actor(tv)).start();
        new Thread(new Audience(tv)).start();
    }

}
/**
 * 产品
 */
class View{
    private String view;

    public View(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
/**
 * 演员表演
 */
class Actor implements Runnable{
    private final Tv tv;

    public Actor(Tv tv) {
        this.tv = tv;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            View v;
            if (i % 2 == 0){
                v = tv.act("人与自然" + i);
            }else {
                v = tv.act("今日说法" + i);
            }
            System.out.println("演员表演" + v.getView());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
/**
 * 观众观看
 */
class Audience implements Runnable{
    private final Tv tv;

    public Audience(Tv tv) {
        this.tv = tv;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("观众观看" + tv.watch().getView());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
/**
 * Tv--信号灯法
 */
class Tv{
    //节目
    private View view;
    //演员表演完，观众才能观看，用flag做标志位
    private boolean flag = false;
    //表演
    public synchronized View act(String v){
        if (flag){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        view = new View(v);
        flag = true;
        this.notifyAll();
        return view;
    }
    //观看
    public synchronized View watch(){
        if (!flag){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        flag = false;
        this.notifyAll();
        return view;
    }
}
