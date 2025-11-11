package org.open.code.learning.base.advanced_02.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 1-1-2、掌握函数式编程在Java中的应用
 *@author: Locyk
 *@time: 2025/9/8
 *
 */
public class StreamMain {
    public static void main(String[] args) {

        List<Person> people = Arrays.asList(new Person("Alice", 25, "Female"),
                new Person("Bob", 30, "Male"),
                new Person("Charlie", 22, "Male")
        );

        // 查找所有男性，年龄大于20，提取姓名并排序
        List<String> result = people.stream()
                .filter(p -> "Male".equals(p.getGender())) // 过滤男性
                .filter(p -> p.getAge() > 20)              // 年龄>20
                .map(Person::getName)                      // 提取姓名
                .sorted()                                  // 排序
                .collect(Collectors.toList());             // 收集结果
        System.out.println(result);

        List<Integer> largeList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10001, 10002, 10003);

        // 并行处理大数据集（自动利用多核）
        long count = largeList.parallelStream()
                .filter(num -> num > 1000)
                .count();
        System.out.println("Large list count: " + count);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Person {
        private String name;
        private int age;
        private String gender;
    }
}
