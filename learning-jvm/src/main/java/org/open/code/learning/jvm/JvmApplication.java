package org.open.code.learning.jvm;

import org.open.code.learning.jvm.loader.ClassLoaderDemo;
import org.open.code.learning.jvm.loader.CustomClassLoader;
import org.open.code.learning.jvm.loader.JdbcClassLoaderDemo;
import org.open.code.learning.jvm.plugins.jdbc.DataStorage;
import org.open.code.learning.jvm.plugins.jdbc.StorageManager;
import org.open.code.learning.jvm.plugins.loader.PluginManager;

/**
 *
 *@author: Locyk
 *@time: 2025/12/31
 *
 */
public class JvmApplication {
    public static void main(String[] args) throws  Exception{

        System.out.println("-----------------------------标准双亲委派加载流程-----------------------------");
        ClassLoaderDemo.loader();

        System.out.println("-----------------------------自定义类加载器-----------------------------");
        CustomClassLoader.loader();

        System.out.println("-----------------------------模拟JDBC加载-----------------------------");
        JdbcClassLoaderDemo.loader();

        System.out.println("-----------------------------【插件】实战JDBC加载-----------------------------");
        DataStorage mysqlStorage = StorageManager.getStorage("MYSQL");
        mysqlStorage.connect();
        mysqlStorage.execute("查询用户数据");

        DataStorage oracleStorage = StorageManager.getStorage("ORACLE");
        oracleStorage.connect();
        oracleStorage.execute("插入订单数据");

        System.out.println("-----------------------------【插件】自定义类加载支持热部署-----------------------------");
        PluginManager.loader();

    }
}
