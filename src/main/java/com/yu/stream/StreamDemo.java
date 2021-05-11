package com.yu.stream;

import java.util.Arrays;
import java.util.List;

class User {
    private Integer id;
    private String name;
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}


/**
 * 题目：找出符合的条件
 * 偶数ID且年龄大于24且用户名转化为大写且用户名字母倒排序，只输出一个用户名字
 */
public class StreamDemo {
    public static void main(String[] args) {
        User user1 = new User(11, "a", 23);
        User user2 = new User(12, "b", 24);
        User user3 = new User(13, "c", 22);
        User user4 = new User(14, "d", 28);
        User user5 = new User(16, "e", 26);

        List<User> users = Arrays.asList(user1, user2, user3, user4, user5);

        users.stream()
                .filter(u -> {
                    return u.getId() % 2 == 0;
                })
                .filter(t -> {
                    return t.getAge() > 24;
                })
                .map(t -> {
                    return t.getName().toUpperCase();
                })
                .sorted((t1, t2) -> {
                    return t2.compareTo(t1);
                }).limit(1).forEach(System.out::println);
    }
}
