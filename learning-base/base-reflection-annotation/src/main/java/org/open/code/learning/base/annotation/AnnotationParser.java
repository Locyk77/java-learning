package org.open.code.learning.base.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 解析注解的工具类
 *
 *@author: Locyk
 *@time: 2025/9/10
 *
 */
public class AnnotationParser {
    //解析类级别注解
    public static void parseClassAnnotation(Class<?> clazz) {
        if (clazz.isAnnotationPresent(ServiceInfo.class)) {
            ServiceInfo serviceInfo = clazz.getAnnotation(ServiceInfo.class);
            System.out.println("=== 服务信息 ===");
            System.out.println("服务名称：" + serviceInfo.name());
            System.out.println("版本号：" + serviceInfo.version());
            System.out.println("作者：" + serviceInfo.author() + "\n");
        }
    }

    // 解析方法级别注解
    public static void parseMethodAnnotation(Class<?> clazz) throws Exception {
        System.out.println("=== 方法日志信息 ===");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Log.class)) {
                Log log = method.getAnnotation(Log.class);
                System.out.println("方法名：" + method.getName());
                System.out.println("是否需要日志：" + log.needLog());
                System.out.println("日志描述：" + log.description() + "\n");
            }
        }
    }

    // 解析字段级别注解并进行数据校验
    public static boolean validateFields(Object obj) throws Exception {
        System.out.println("=== 字段校验结果 ===");
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        boolean isValid = true;

        for (Field field : fields) {
            if (field.isAnnotationPresent(Validate.class)) {
                Validate validate = field.getAnnotation(Validate.class);
                field.setAccessible(true); // 允许访问私有字段
                Object value = field.get(obj);

                // 非空校验
                if (validate.notNull() && value == null) {
                    System.out.println("字段[" + field.getName() + "]校验失败：不能为null");
                    isValid = false;
                }

                // 最小长度校验（仅对字符串）
                if (value instanceof String && validate.minLength() > 0) {
                    String strValue = (String) value;
                    if (strValue.length() < validate.minLength()) {
                        System.out.println("字段[" + field.getName() + "]校验失败：长度不能小于" + validate.minLength());
                        isValid = false;
                    }
                }
            }
        }
        return isValid;
    }

}
