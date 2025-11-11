package org.open.code.learning.base.annotation;

/**
 *
 *@author: Locyk
 *@time: 2025/9/10
 *
 */
public class AnnotationMain {
    public static void main(String[] args) throws Exception {
        UserService userService1 = new UserService("张三", 25);
        UserService userService2 = new UserService("李", null);

        // 解析类注解
        AnnotationParser.parseClassAnnotation(UserService.class);

        // 解析方法注解
        AnnotationParser.parseMethodAnnotation(UserService.class);

        // 解析字段注解并校验第一个对象
        System.out.println("第一个用户对象校验结果：" +
                AnnotationParser.validateFields(userService1) + "\n");

        // 解析字段注解并校验第二个对象（预期失败）
        System.out.println("第二个用户对象校验结果：" +
                AnnotationParser.validateFields(userService2));
    }
}
