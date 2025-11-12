package org.open.code.learning.base.generic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author: Locyk
 *@time: 2025/11/12
 *
 */
public class ErasureTest {
    public static void main(String[] args) {
        // 测试GenericMethodConflict（无冲突）
        GenericMethodConflict conflict = new GenericMethodConflict();
        conflict.test("Java"); // 调用test(T)：T类型：Java
        conflict.test(new ArrayList<>(List.of(1, 2, 3))); // 调用test(List<T>)：List<T>类型：[1,2,3]

        // 验证List<String>和List<Integer>的getClass()是否相同
        List<String> strList = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();
        System.out.println("strList.getClass()：" + strList.getClass()); // class java.util.ArrayList
        System.out.println("intList.getClass()：" + intList.getClass()); // class java.util.ArrayList
        System.out.println("两个List的Class是否相同：" + (strList.getClass() == intList.getClass())); // true
        // 原因：类型擦除后，List<String>和List<Integer>都变为原始类型List
    }

}

// 题目1：方法重载冲突（类型擦除导致）
// 以下代码编译报错：擦除后两个方法签名均为 test(List)
// public class ErasureConflict {
//     public void test(List<String> list) { System.out.println("String列表"); }
//     public void test(List<Integer> list) { System.out.println("Integer列表"); }
// }

// 题目2：泛型方法重载（编译通过：方法签名不同）
class GenericMethodConflict {
    // 方法1：参数为T
    public <T> void test(T data) {
        System.out.println("T类型：" + data);
    }

    // 方法2：参数为List<T>（与方法1参数类型不同，无冲突）
    public <T> void test(List<T> data) {
        System.out.println("List<T>类型：" + data);
    }
}
