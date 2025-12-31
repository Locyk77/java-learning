package org.open.code.learning.jvm.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 自定义类加载器（不破坏双亲委派）
 *
 *@author: Locyk
 *@time: 2025/12/30
 *
 */
public class CustomClassLoader extends ClassLoader {

    // 自定义加载范围：指定目录
    private final String classPath;

    public CustomClassLoader(String classPath) {
        // 父加载器默认是AppClassLoader（保留双亲委派）
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            // 1. 拼接类文件路径（com.example.MyClass -> /opt/myclasses/com/example/MyClass.class）
            String classFile = classPath + File.separator + className.replace('.', File.separatorChar) + ".class";

            // 2. 读取类字节码
            byte[] classBytes = readClassBytes(classFile);

            // 3. 定义类（将字节码转为Class对象）
            return defineClass(className, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("类加载失败：" + className, e);
        }
    }

    // 读取类文件字节码
    private byte[] readClassBytes(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             FileChannel channel = fis.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
            channel.read(buffer);
            return buffer.array();
        }
    }

    public static void loader() throws Exception {
        // 1. 初始化自定义加载器（指定类文件目录，需替换为你的实际路径）
        String classPath = "F:\\Home\\Workspace\\Project-exp\\exp-learning\\learning-jvm\\target\\classes"; // 假设MyClass.class放在这个目录下
        CustomClassLoader customCL = new CustomClassLoader(classPath);

        // 2. 加载类（遵循双亲委派：先委托AppClassLoader，再自己加载）
        Class<?> clazz = customCL.loadClass("org.open.code.learning.jvm.loader.MyClass");
        System.out.println("自定义加载器加载的类：" + clazz);
        System.out.println("类加载器：" + clazz.getClassLoader());

        // 3. 反射创建实例并调用方法
        Object obj = clazz.getDeclaredConstructor().newInstance();
        clazz.getMethod("sayHello").invoke(obj);
    }

}
