package com.juc.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * 要求使用Stream流完成筛选
 * id必须是偶数、年龄大于23，用户名转为大写，用户名倒序排列，只输出一个用户
 */
public class Test {
    public static void main(String[] args) {
        User user1 = new User(1,"a",21);
        User user2 = new User(2,"b",22);
        User user3 = new User(3,"c",23);
        User user4 = new User(4,"d",24);
        User user5 = new User(5,"e",25);
        User user6 = new User(6,"f",26);
        List<User> list = Arrays.asList(user1,user2,user3,user4,user5,user6);
        list.stream().filter(u -> u.getId() % 2 == 0)
                .filter(u -> u.getAge() > 23)
                .map(u -> u.getName().toUpperCase(Locale.ROOT))
                .sorted((o1, o2) -> o2.compareTo(o1)).limit(1)
                .forEach(System.out::println);
    }
}
