package org.open.code.learning.base.generic;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 *
 *@author: Locyk
 *@time: 2025/11/12
 *
 */
public class GenericInheritanceTest {

    public static void main(String[] args) {
        // 测试StringChild
        StringChild stringChild = new StringChild();
        stringChild.process("hello"); // 子类处理字符串：HELLO
        System.out.println(stringChild.getResult()); // 字符串处理完成

        // 测试GenericChild
        GenericChild<String, Integer> genericChild = new GenericChild<>();
        genericChild.process(new Pair<>("Java", 18)); // 子类处理Pair：Pair{Java, 18}
        System.out.println(genericChild.getResult()); // Pair{默认, 结果}

        // 反射查看StringChild的所有方法（验证桥接方法）
        System.out.println("\nStringChild的所有方法（含桥接方法）：");
        Method[] methods = StringChild.class.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("方法名：" + method.getName() + "，参数类型：" + Arrays.toString(method.getParameterTypes()));
        }
        // 输出会包含桥接方法：
        // process(java.lang.Object) → 编译器自动生成，调用process(java.lang.String)
        // getResult() → 父类返回T，子类返回String，桥接方法保证多态
    }
}

// 泛型父类
class Parent<T> {
    public void process(T data) {
        System.out.println("父类处理数据：" + data);
    }

    public T getResult() {
        return null;
    }
}

// 自定义Pair类（用于GenericChild）
class Pair<T, U> {
    T first;
    U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "Pair{" + first + ", " + second + "}";
    }
}

// 子类1：指定泛型为String
class StringChild extends Parent<String> {
    @Override
    public void process(String data) {
        System.out.println("子类处理字符串：" + data.toUpperCase());
    }

    @Override
    public String getResult() {
        return "字符串处理完成";
    }
}

// 子类2：泛型继承（T,U），父类泛型为Pair<T,U>
class GenericChild<T, U> extends Parent<Pair<T, U>> {
    @Override
    public void process(Pair<T, U> data) {
        System.out.println("子类处理Pair：" + data);
    }

    @Override
    public Pair<T, U> getResult() {
        return new Pair<T, U>((T) "默认", (U) "结果");
    }
}
