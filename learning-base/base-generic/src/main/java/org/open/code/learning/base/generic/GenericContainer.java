package org.open.code.learning.base.generic;

/**
 * 1、定义泛型类 GenericContainer<T>，包含一个私有泛型成员变量 data；
 * 2、提供构造方法 GenericContainer(T data) 初始化数据；
 * 3、提供 getData() 方法获取数据，setData(T data) 修改数据；
 * 4、测试：创建 GenericContainer<String>（存储 "泛型测试"）、GenericContainer<Double>（存储 3.1415）、GenericContainer<User>（自定义 User 类，包含 id 和 name），分别调用方法并打印结果。
 *
 *@author: Locyk
 *@time: 2025/11/12
 *
 */
public class GenericContainer<T> {

    private T data;

    public GenericContainer(T data) {
        this.data = data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    static class User {
        private int id;
        private String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        // 1. 存储String类型
        GenericContainer<String> strContainer = new GenericContainer<>("泛型测试");
        System.out.println("String容器初始值：" + strContainer.getData());
        strContainer.setData("泛型修改测试");
        System.out.println("String容器修改后：" + strContainer.getData());

        // 2. 存储Double类型
        GenericContainer<Double> doubleContainer = new GenericContainer<>(3.1415);
        System.out.println("Double容器值：" + doubleContainer.getData());

        // 3. 存储自定义User类型
        GenericContainer<User> userContainer = new GenericContainer<>(new User(1001, "张三"));
        System.out.println("User容器值：" + userContainer.getData());
        userContainer.setData(new User(1002, "李四"));
        System.out.println("User容器修改后：" + userContainer.getData());
    }
}
