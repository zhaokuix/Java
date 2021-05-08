package demo1;

/**
 * 懒汉式单例模式,存在线程风险
 */
public class SingletonTwo {
    //创建私有构造方法
    private SingletonTwo(){

    }
    //创建静态的该类实例对象
    private static SingletonTwo singletonTwo = null;
    //创建公有静态方法返回静态实例对象
    public static SingletonTwo getInstance(){
        if (singletonTwo == null){
            singletonTwo = new SingletonTwo();
        }
        return singletonTwo;
    }
}
