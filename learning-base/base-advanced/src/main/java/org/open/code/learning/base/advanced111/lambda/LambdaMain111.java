package org.open.code.learning.base.advanced111.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 1-1-1、理解Java8及以上版本的新特性（如Lambda表达式、Stream API、Optional）
 *@author: Locyk
 *@time: 2025/9/8
 *
 */
public class LambdaMain111 {

    public static void main(String[] args) {

        // 传统匿名内部类
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("传统方式");
            }
        };
        // Lambda表达式
        Runnable runnable2 = () -> System.out.println("Lambda方式1");

        new Thread(runnable1).start();
        new Thread(runnable2).start();

        // Lambda表达式
        new Thread(() -> {
            System.out.println("Lambda方式2");
        }).start();

        // 带参数的Lambda
        Comparator<Integer> comparator = (a, b) -> a - b;
        List<Integer> list = Arrays.asList(3, 1, 2);
        list.sort(comparator); // 排序结果：[1, 2, 3]
        System.out.println(list);
    }
}
