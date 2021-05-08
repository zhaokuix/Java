package annotation;

import java.lang.annotation.*;

/**
 * 自定义注解测试
 */
@MyAnnotation
public class demo1 {

    @MyAnnotation
    void test(){

    }
}
//target:说明注解可以用在什么地方
@Target(value = {ElementType.TYPE, ElementType.METHOD})
//指定注解的生命周期
@Retention(value = RetentionPolicy.RUNTIME)
//注解将被包含在javadoc中
@Documented
//子类可以继承父类的注解
@Inherited
@interface MyAnnotation{

    String value() default "";
}
