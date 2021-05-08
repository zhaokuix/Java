package demo2;

/**
 * 静态代理模式
 * 真实对象和代理对象实现同一接口
 * 代理对象要代理真实角色
 *      代理对象可以做很多真实对象做不了的事情
 *      真实对象专注于自己的事情
 */
public class StaticProxy {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我是Runnable");
            }
        }).start();
        //lambda写法
        new Thread(() -> System.out.println("lambda--->我是Runnable")).start();
        Company company = new Company(new Person());
        company.happyMarry();
    }
}

interface Marry{
    void happyMarry();
}

class Person implements Marry{
    @Override
    public void happyMarry() {
        System.out.println("HappyMarry!!!");
    }
}
class Company implements Marry{
    private Marry marry;
    public Company(Marry marry){
        this.marry = marry;
    }
    @Override
    public void happyMarry() {
        System.out.println("before");
        marry.happyMarry();
        System.out.println("complete");
    }
}