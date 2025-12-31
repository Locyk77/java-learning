package org.open.code.learning.jvm.plugins.loader;

import java.util.Scanner;

/**
 *
 *@author: Locyk
 *@time: 2025/12/30
 *
 */
public class PluginManager {
    // 插件根目录（对应项目下的plugins/）
    private static final String PLUGIN_DIR = "F:\\Home\\Data\\Plugins";
    // 插件完整类名
    private static final String PLUGIN_CLASS_NAME = "org.demo03.plugin.loader.HelloPlugin";
    // 自定义类加载器（每次热部署创建新实例）
    private static PluginClassLoader pluginClassLoader;



    public static void loader() throws Exception {
        System.out.println("===== 插件化+热部署演示 =====");
        System.out.println("命令说明：");
        System.out.println("  execute - 执行插件");
        System.out.println("  reload  - 热部署插件（先修改插件类再执行）");
        System.out.println("  exit    - 退出程序");
        System.out.println("==========================\n");

        // 初始化类加载器
        resetPluginClassLoader();

        // 控制台交互
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\n请输入命令：");
            String command = scanner.nextLine().trim();
            switch (command) {
                case "execute":
                    runPlugin();
                    break;
                case "reload":
                    resetPluginClassLoader();
                    System.out.println("【热部署】插件类加载器已重新初始化");
                    break;
                case "exit":
                    System.out.println("程序退出");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("无效命令，请重新输入！");
            }
        }
    }

    /**
     * 重置类加载器（热部署核心：抛弃旧加载器，创建新加载器）
     */
    private static void resetPluginClassLoader() {
        // 旧加载器清空缓存，等待GC回收
        if (pluginClassLoader != null) {
            pluginClassLoader.clearCache();
        }
        // 新建加载器，加载最新插件类
        pluginClassLoader = new PluginClassLoader(PLUGIN_DIR);
    }

    /**
     * 加载并执行插件
     */
    private static void runPlugin() throws Exception {
        try {
            Class<?> pluginClass = pluginClassLoader.loadClass(PLUGIN_CLASS_NAME);
            Plugin plugin = (Plugin) pluginClass.getDeclaredConstructor().newInstance();

            System.out.println("\n---------- 插件执行结果 ----------");
            System.out.println("插件名称：" + plugin.getName());
            plugin.execute();
            System.out.println("---------------------------------");
            System.out.println("插件加载器：" + pluginClass.getClassLoader());
            System.out.println("接口加载器：" + Plugin.class.getClassLoader());
            System.out.println("---------------------------------\n");

        } catch (ClassNotFoundException e) {
            System.err.println("【错误】找不到插件类！请检查：");
            System.err.println("1. 插件项目是否编译成功");
            System.err.println("2. 插件class文件是否复制到主程序的plugins目录");
        } catch (Exception e) {
            System.err.println("【错误】插件执行失败：");
            e.printStackTrace();
        }
    }
}
