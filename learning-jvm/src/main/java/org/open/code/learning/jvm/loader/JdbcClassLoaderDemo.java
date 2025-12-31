package org.open.code.learning.jvm.loader;

/**
 * 模拟JDBC加载驱动
 *
 *@author: Locyk
 *@time: 2025/12/30
 *
 */
public class JdbcClassLoaderDemo {
    // ==================== 1. 模拟JDBC的DriverManager（模拟由Bootstrap加载） ====================
    static class DriverManager {
        static {
            // 打印DriverManager的类加载器（Java代码无法真正模拟Bootstrap，仅作说明）
            System.out.println("[DriverManager] 加载器：" + DriverManager.class.getClassLoader() + "（注：Java代码无法模拟Bootstrap，实际JDBC的DriverManager此处为null）");
            // 修复：使用完整内部类名加载驱动
            loadDriver(JdbcClassLoaderDemo.class.getName() + "$MySQLDriver");
        }

        public static void loadDriver(String driverClassName) {
            try {
                // 核心：获取线程上下文类加载器（TCCL）
                ClassLoader tccl = Thread.currentThread().getContextClassLoader();
                System.out.println("[DriverManager] 获取的TCCL：" + tccl);

                // 核心：用TCCL加载完整类名的驱动类
                Class<?> driverClass = tccl.loadClass(driverClassName);
                System.out.println("[DriverManager] 驱动类 " + driverClassName + " 加载器：" + driverClass.getClassLoader());
                System.out.println("[DriverManager] 驱动 " + driverClassName + " 注册成功");

            } catch (ClassNotFoundException e) {
                System.err.println("[DriverManager] 驱动加载失败：" + e.getMessage());
                e.printStackTrace(); // 打印异常栈，方便排查
            }
        }
    }

    // ==================== 2. 模拟MySQL驱动类 ====================
    static class MySQLDriver {
        static {
            System.out.println("[MySQLDriver] 被加载，加载器：" + MySQLDriver.class.getClassLoader());
        }

        public MySQLDriver() {
            System.out.println("[MySQLDriver] 驱动实例化成功");
        }
    }

    // ==================== 3. 测试主类 ====================
    public static void loader() throws  Exception{
        // 步骤1：打印主线程的TCCL（JDK 9+为AppClassLoader，父类是PlatformClassLoader）
        ClassLoader mainTccl = Thread.currentThread().getContextClassLoader();
        System.out.println("===== 主线程初始化（JDK 9+） =====");
        System.out.println("[Main] 主线程TCCL（AppClassLoader）：" + mainTccl);
        System.out.println("[Main] TCCL父加载器（PlatformClassLoader）：" + mainTccl.getParent());
        System.out.println("[Main] PlatformClassLoader父加载器（Bootstrap）：" + mainTccl.getParent().getParent() + "（null表示Bootstrap）");

        // 步骤2：触发DriverManager加载（模拟JDBC初始化）
        System.out.println("\n===== 模拟JDBC加载驱动 =====");
        new DriverManager();

        // 步骤3：直接实例化驱动类，验证加载器
        System.out.println("\n===== 直接实例化驱动类 =====");
        MySQLDriver driver = new MySQLDriver();
        System.out.println("[Main] 直接加载的MySQLDriver加载器：" + driver.getClass().getClassLoader());
    }
}



