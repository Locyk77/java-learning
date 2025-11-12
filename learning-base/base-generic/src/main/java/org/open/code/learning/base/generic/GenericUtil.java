package org.open.code.learning.base.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 1、定义工具类 GenericUtil，包含 3 个静态泛型方法：
 *     T getMiddleElement(T[] array)：返回数组的中间元素（长度为偶数时返回第 length/2 - 1 个）；
 *     List<T> arrayToList(T[] array)：将任意类型数组转为 List<T>；
 *     boolean isEqual(T a, T b)：判断两个对象是否相等（用 Objects.equals()）；
 * 2、测试：用 String[]、Integer[] 验证上述方法，打印结果。
 *
 *@author: Locyk
 *@time: 2025/11/12
 *
 */
public class GenericUtil {

    public static <T> T getMiddleElement(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        int middleIndex = array.length % 2 == 1 ? array.length / 2 : (array.length / 2) - 1;
        return array[middleIndex];
    }

    public static <T> List<T> arrayToList(T[] array) {
        if (array == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(array));
    }

    public static <T> boolean isEqual(T a, T b) {
        return Objects.equals(a, b);
    }

    public static void main(String[] args) {
        String[] stringArray = {"apple", "banana", "cherry", "date"};
        System.out.println(getMiddleElement(stringArray));
        Integer[] integerArray = {1, 2, 3, 4, 5};
        System.out.println(getMiddleElement(integerArray));
        System.out.println(arrayToList(stringArray));
        Integer a = Integer.valueOf(139);
        Integer b = Integer.valueOf(139);
        System.out.println(isEqual(a, b));
    }
}
