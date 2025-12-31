package org.open.code.learning.jvm.plugins.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *@author: Locyk
 *@time: 2025/12/30
 *
 */
public class PluginClassLoader extends ClassLoader{
    private final String pluginDir;
    private final Map<String, Class<?>> classCache = new HashMap<>();

    public PluginClassLoader(String pluginDir) {
        super(ClassLoader.getSystemClassLoader());
        this.pluginDir = pluginDir;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                // 核心类委托系统加载器，插件类自定义加载
                if (name.startsWith("java.") || name.startsWith("org.open.code.learning.jvm.plugins.")) {
                    c = super.loadClass(name, resolve);
                } else {
                    c = findClass(name);
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    @Override
    protected Class<?> findClass(String fullClassName) throws ClassNotFoundException {
        if (classCache.containsKey(fullClassName)) {
            return classCache.get(fullClassName);
        }

        // 插件目录：IDEA中项目根目录的plugins文件夹
        String classFilePath = pluginDir + File.separator + fullClassName.replace('.', File.separatorChar) + ".class";
        System.out.println("【加载插件】" + classFilePath);
        File classFile = new File(classFilePath);
        if (!classFile.exists()) {
            throw new ClassNotFoundException("插件类不存在：" + fullClassName + "\n路径：" + classFile.getAbsolutePath());
        }

        try {
            byte[] classBytes = readClassBytes(classFile);
            Class<?> clazz = defineClass(fullClassName, classBytes, 0, classBytes.length);
            classCache.put(fullClassName, clazz);
            return clazz;
        } catch (IOException e) {
            throw new ClassNotFoundException("加载插件失败：" + fullClassName, e);
        }
    }

    // 清空缓存（热部署）
    public void clearCache() {
        classCache.clear();
        System.out.println("【热部署】插件缓存已清空");
    }

    // 读取class字节码
    private byte[] readClassBytes(File classFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(classFile);
             FileChannel channel = fis.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
            channel.read(buffer);
            return buffer.array();
        }
    }
}
