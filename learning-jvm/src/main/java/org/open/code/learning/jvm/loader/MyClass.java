package org.open.code.learning.jvm.loader;

/**
 * 标准双亲委派加载流程（创建待加载的类文件）
 *
 *@author: Locyk
 *@time: 2025/12/30
 *
 */
public class MyClass {
    static {
        // 静态代码块，加载时执行，方便观察加载时机
        System.out.println("MyClass 被加载，加载器：" + MyClass.class.getClassLoader());
    }

    public void sayHello() {
        System.out.println("Hello from MyClass!");
    }
}
