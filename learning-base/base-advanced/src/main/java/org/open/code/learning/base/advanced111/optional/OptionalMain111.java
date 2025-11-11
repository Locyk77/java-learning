package org.open.code.learning.base.advanced111.optional;

import java.util.Optional;

/**
 * 1-1-1、理解Java8及以上版本的新特性（如Lambda表达式、Stream API、Optional）
 *@author: Locyk
 *@time: 2025/9/8
 *
 */
public class OptionalMain111 {
    public static void main(String[] args) {
        // 创建Optional
        Optional<String> optional1 = Optional.of("Hello");
        Optional<String> optional2 = Optional.ofNullable(null);

        // 安全获取值
        String value1 = optional1.orElse("默认值"); // "Hello"
        String value2 = optional2.orElse("默认值"); // "默认值"
        System.out.println(value1);
        System.out.println(value2);

        // 存在时执行操作
        optional1.ifPresent(str -> System.out.println(str.length())); // 输出 5
    }
}
