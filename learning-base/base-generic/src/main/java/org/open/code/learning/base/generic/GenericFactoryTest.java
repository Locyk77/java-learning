package org.open.code.learning.base.generic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 *@author: Locyk
 *@time: 2025/11/12
 *
 */
public class GenericFactoryTest {

    public static void main(String[] args) {
        // 1. 创建String实例（参数：String）
        String str = GenericFactory.create(String.class, "反射创建字符串");
        System.out.println("String实例：" + str); // 反射创建字符串

        // 2. 创建Integer实例（参数：int）
        Integer num = GenericFactory.create(Integer.class, 123);
        System.out.println("Integer实例：" + num); // 123

        // 3. 创建Order实例（参数：String, double）
        Order order = GenericFactory.create(Order.class, "o5", 66.6);
        System.out.println("Order实例金额：" + order.getAmount()); // 66.6

        // 4. 尝试创建List<String>实例（失败：List是接口，无构造方法）
        // List<String> list = GenericFactory.create(List.class);
        // System.out.println(list); // 输出null，控制台打印未找到构造方法
    }
}

// 泛型工厂类
class GenericFactory {
    // 泛型方法：通过反射创建实例（支持任意参数构造）
    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> clazz, Object... args) {
        try {
            // 1. 获取参数类型数组
            Class<?>[] paramTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                paramTypes[i] = args[i].getClass();
                // 特殊处理：基本类型包装类（如int→Integer）
                if (paramTypes[i] == Integer.class){
                    paramTypes[i] = int.class;
                }
                if (paramTypes[i] == Double.class){
                    paramTypes[i] = double.class;
                }
            }

            // 2. 获取指定参数的构造方法
            Constructor<T> constructor = clazz.getDeclaredConstructor(paramTypes);
            constructor.setAccessible(true); // 允许访问私有构造（可选）

            // 3. 实例化并返回
            return constructor.newInstance(args);
        } catch (NoSuchMethodException e) {
            System.err.println("未找到匹配的构造方法：" + e.getMessage());
        } catch (InstantiationException e) {
            System.err.println("实例化失败：" + e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println("访问权限不足：" + e.getMessage());
        } catch (InvocationTargetException e) {
            System.err.println("构造方法执行异常：" + e.getMessage());
        }
        return null;
    }
}
