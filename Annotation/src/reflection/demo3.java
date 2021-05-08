package reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 所有类型的Class
 */
public class demo3 {

    public static void main(String[] args) {
        //类
        Class c1 = Object.class;
        //接口
        Class c2 = Comparable.class;
        //一维数组
        Class c3 = int[].class;
        //二维数组
        Class c4 = int[][].class;
        //注解
        Class c5 = Target.class;
        //枚举
        Class c6 = ElementType.class;
        //基本数据类型
        Class c7 = int.class;
        //void
        Class c8 = void.class;
        //Class
        Class c9 = Class.class;

        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);
        System.out.println(c4);
        System.out.println(c5);
        System.out.println(c6);
        System.out.println(c7);
        System.out.println(c8);
        System.out.println(c9);

        //数组只要元素类型与维度一样，就是同一个Class
        int[] a = new int[10];
        int[] b = new int[100];
        System.out.println(a.getClass().hashCode());
        System.out.println(b.getClass().hashCode());
    }
}
