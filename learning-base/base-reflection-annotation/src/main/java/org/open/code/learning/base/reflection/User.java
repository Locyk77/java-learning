package org.open.code.learning.base.reflection;

/**
 *
 *@author: Locyk
 *@time: 2025/9/10
 *
 */
public class User {

    private String name;
    private int age;
    public String email;

    public User() {

    }

    private User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public void sayHello() {
        System.out.println("Hello, I'm " + name);
    }

    private String getInfo(String prefix) {
        return prefix + ": " + name + ", " + age + "years old";
    }
}
