package org.open.code.learning.base.generic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author: Locyk
 *@time: 2025/11/12
 *
 */
public class GenericUpDown {

    // 1. 上界通配符：只能读（生产者），计算数值总和
    //TODO 只能是Number或者Number的子类
    public static long sumAll(List<? extends Number> list) {
        long sum = 0;
        for (Number number : list) {
            sum += number.longValue();
        }
        return sum;
    }

    // 2. 下界通配符：只能写（消费者），添加字符串
    //TODO 只能是String或者String的父类Object
    public static void addStrings(List<? super String> list){
        list.add("Java");
        list.add("泛型");
        list.add("通配符");
    }

    // 3. 无界通配符：任意类型列表，只能读（安全）
    public static void printAll(List<?> list) {
        for (Object obj : list) {
            System.out.print(obj + " ");
        }
        System.out.println();
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
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }



    public static void main(String[] args) {
        // 测试sumAll
        List<Integer> intList = new ArrayList<>(List.of(1, 2, 3));
        List<Double> doubleList = new ArrayList<>(List.of(1.5, 2.5));
        // List<String> strList = new ArrayList<>(List.of("1", "2")); // 编译报错：String不是Number子类
        System.out.println("Integer列表总和：" + sumAll(intList)); // 6
        System.out.println("Double列表总和：" + sumAll(doubleList)); // 4（1.5+2.5=4.0→long为4）

        // 测试addStrings
        List<Object> objList = new ArrayList<>();
        List<String> strList = new ArrayList<>();
        // List<CharSequence> charList = new ArrayList<>(); // 编译通过（String是CharSequence子类）
        addStrings(objList);
        addStrings(strList);
        System.out.println("Object列表添加后：" + objList); // [Java, 泛型, 通配符]
        System.out.println("String列表添加后：" + strList); // [Java, 泛型, 通配符]

        // 测试printAll
        printAll(intList); // 1 2 3
        printAll(strList); // Java 泛型 通配符
        printAll(new ArrayList<>(List.of(new User(1001, "张三")))); // User{id=1001, name='张三'}
    }
}
