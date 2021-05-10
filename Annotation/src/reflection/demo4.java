package reflection;

public class demo4 {
    public static void main(String[] args) throws ClassNotFoundException {
        //获取系统类加载器  加载自定义类  AppClassLoader
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);
        //获取扩展类加载器  加载扩展包   ExtClassLoader
        ClassLoader parent = systemClassLoader.getParent();
        System.out.println(parent);
        //获取引导类加载器  加载rt包   BootstrapClassLoader
        ClassLoader parent1 = parent.getParent();
        System.out.println(parent1);

        //测试当前类是哪个类加载器加载的
        System.out.println(Class.forName("reflection.demo4").getClassLoader());

        //测试JDK内部类是哪个类加载器加载的
        System.out.println(Class.forName("java.lang.Object").getClassLoader());

        //获取系统类加载器可以加载的路径
        System.out.println(System.getProperty("java.class.path"));

        //双亲委派机制    会往上推看上级有没有定义，如果上级有自定义的包将被替换
        //比如自定义java.lang.String是无法使用的

        /*
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\charsets.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\ext\access-bridge-64.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\ext\cldrdata.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\ext\dnsns.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\ext\jaccess.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\ext\localedata.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\ext\nashorn.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\ext\sunec.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\ext\sunjce_provider.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\ext\sunmscapi.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\ext\sunpkcs11.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\ext\zipfs.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\jce.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\jfr.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\jsse.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\jvmci-services.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\management-agent.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\resources.jar;
        C:\APP\graalvm-ce-java8-21.1.0\jre\lib\rt.jar;
        D:\Java\Annotation\out\production\Annotation;
        D:\Program Files\IntelliJ IDEA 2020.3.3\lib\idea_rt.jar
         */

    }
}
