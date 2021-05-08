package demo1;

public class TestParent {

    static {
        System.out.println("父级静态代码块");
    }
    {
        System.out.println("父级代码块");
    }
    public TestParent(){
        System.out.println("父级构造函数");
    }
}
