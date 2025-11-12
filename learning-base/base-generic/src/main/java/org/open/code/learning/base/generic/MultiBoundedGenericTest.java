package org.open.code.learning.base.generic;

import java.util.List;

/**
 *
 *@author: Locyk
 *@time: 2025/11/12
 *
 */
public class MultiBoundedGenericTest {
    public static void main(String[] args) {
        // 测试Integer类型
        AdvancedCalculator<Integer> intCalc = new AdvancedCalculator<>();
        Integer intSum = intCalc.add(15, 25);
        List<Integer> intList = List.of(5, 3, 20, 8);
        Integer intMax = intCalc.max(intList);
        System.out.println("Integer相加结果：" + intSum); // 40
        System.out.println("Integer列表最大值：" + intMax); // 20

        // 测试Double类型
        AdvancedCalculator<Double> doubleCalc = new AdvancedCalculator<>();
        Double doubleSum = doubleCalc.add(3.2, 5.8);
        List<Double> doubleList = List.of(1.5, 4.9, 2.3);
        Double doubleMax = doubleCalc.max(doubleList);
        System.out.println("Double相加结果：" + doubleSum); // 9.0
        System.out.println("Double列表最大值：" + doubleMax); // 4.9

        // 多重边界注意事项：
        // 1. 类必须放在接口前面（如 T extends Number & Comparable，不能反过来）
        // 2. 只能有一个类边界（不能 T extends Number & String）
        // 3. 多个接口边界用 & 连接
    }
}

// 泛型多重边界：T必须是Number子类，且实现Comparable接口
class AdvancedCalculator<T extends Number & Comparable<T>> {
    // 相加：根据类型自动转换（Integer→Integer，Double→Double）
    @SuppressWarnings("unchecked")
    public T add(T a, T b) {
        if (a instanceof Integer) {
            return (T) Integer.valueOf(a.intValue() + b.intValue());
        } else if (a instanceof Double) {
            return (T) Double.valueOf(a.doubleValue() + b.doubleValue());
        } else if (a instanceof Long) {
            return (T) Long.valueOf(a.longValue() + b.longValue());
        } else {
            throw new UnsupportedOperationException("不支持的数值类型");
        }
    }

    // 求列表最大值
    public T max(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("列表不能为空");
        }
        T max = list.get(0);
        for (T element : list) {
            if (element.compareTo(max) > 0) {
                max = element;
            }
        }
        return max;
    }
}
