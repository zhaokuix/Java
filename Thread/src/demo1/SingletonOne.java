package demo1;

/**
 * 饿汉式单例模式，线程安全，空间换时间
 */
public class SingletonOne {
    //创建私有构造函数
    private SingletonOne(){

    }
    //创建私有静态实例
    private static SingletonOne singletonOne = new SingletonOne();

    //创建公有静态方法返回静态实例对象
    public static SingletonOne getSingletonOne(){
        return singletonOne;
    }
}
