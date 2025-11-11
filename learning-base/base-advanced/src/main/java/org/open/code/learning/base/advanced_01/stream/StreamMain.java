package org.open.code.learning.base.advanced_01.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 1-1-1、理解Java8及以上版本的新特性（如Lambda表达式、Stream API、Optional）
 *@author: Locyk
 *@time: 2025/9/8
 *
 */
public class StreamMain {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        // 过滤长度大于3的名字，转为大写，收集到列表
        List<String> result = names.stream()
                .filter(name -> name.length() > 3)  // 过滤
                .map(String::toUpperCase)           // 转换
                .sorted()                           // 排序
                .collect(Collectors.toList());      // 收集结果

        // 结果：[ALICE, CHARLIE, DAVID]
        System.out.println(result);
    }
}
