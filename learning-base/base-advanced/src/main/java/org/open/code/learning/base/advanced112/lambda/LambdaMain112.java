package org.open.code.learning.base.advanced112.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 1-1-2、掌握函数式编程在Java中的应用
 *@author: Locyk
 *@time: 2025/9/8
 *
 */
public class LambdaMain112 {
    public static void main(String[] args) {

        // Predicate：判断数字是否为偶数
        Predicate<Integer> isEven = num -> num % 2 == 0;
        System.out.println(isEven.test(4));

        // Function：将字符串转为长度
        Function<String, Integer> strLength = str -> str.length();
        System.out.println(strLength.apply("hello"));

        // Consumer：打印数据
        Consumer<String> printer = s -> System.out.println(s);
        printer.accept("函数式编程");

        // Supplier：生成随机数
        Supplier<Double> random = () -> Math.random();
        System.out.println(random.get());


        // 引用静态方法
        Function<String, Integer> parseInt = Integer::parseInt;
        System.out.println(parseInt.apply("123"));

        //自定义方法引用
        Function<String, Integer> test = LambdaMain112::test;
        System.out.println(test.apply("123"));

        // 引用实例方法（对象::方法）
        String str = "hello";
        Supplier<Integer> length = str::length;
        System.out.println(length.get());

        // 引用构造方法
        Supplier<List<String>> listSupplier = ArrayList::new;
        Function<Integer, List<String>> arrayList = ArrayList::new;// 带参数的构造方法
        List<String> arrayList1 = listSupplier.get();
        List<String> arrayList2 = arrayList.apply(10);
        System.out.println(arrayList1.size());
        System.out.println(arrayList2.size());


    }


    public static int test(String s) {
        return 12;
    }
}
