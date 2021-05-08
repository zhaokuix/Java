package demo1;

/**
 * 继承加载顺序
 *
 * 父级静态代码块
 * 静态代码块
 * 父级代码块
 * 父级构造函数
 * 代码块
 * 构造函数
 */
public class TestObject extends TestParent{
    static {
        System.out.println("静态代码块");
    }
    {
        System.out.println("代码块");
    }
    public TestObject(){
        System.out.println("构造函数");
    }

    public static void main(String[] args) {
        new TestObject();
    }
}
