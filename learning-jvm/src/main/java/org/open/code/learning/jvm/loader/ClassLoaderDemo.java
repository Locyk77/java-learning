package org.open.code.learning.jvm.loader;

/**
 * 标准双亲委派加载流程（测试类加载流程）
 *
 *@author: Locyk
 *@time: 2025/12/30
 *
 */
public class ClassLoaderDemo {

    public static void loader() throws ClassNotFoundException {
        // 1. 获取当前线程的上下文类加载器（默认是AppClassLoader）
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println("当前线程上下文类加载器：" + contextClassLoader);

        // 2. 触发类加载（自动加载：首次使用类）
        System.out.println("\n===== 触发MyClass自动加载 =====");
        MyClass myClass = new MyClass();
        myClass.sayHello();

        // 3. 手动加载（验证缓存：第二次加载直接返回缓存）
        System.out.println("\n===== 手动加载MyClass（验证缓存） =====");
        Class<?> clazz = Class.forName("org.open.code.learning.jvm.loader.MyClass");
        System.out.println("手动加载的类加载器：" + clazz.getClassLoader());
        System.out.println("是否是同一个类：" + (clazz == MyClass.class));

        // 4. 打印类加载器层级
        System.out.println("\n===== 类加载器层级 =====");
        ClassLoader appClassLoader = MyClass.class.getClassLoader();
        System.out.println("应用类加载器（AppClassLoader）：" + appClassLoader);
        System.out.println("扩展类加载器（ExtClassLoader）：" + appClassLoader.getParent());
        System.out.println("启动类加载器（Bootstrap）：" + appClassLoader.getParent().getParent()); // null（jdk8之前（含）C++实现）
    }
}
