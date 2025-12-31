package org.open.code.learning.jvm.plugins.jdbc;

/**
 *
 *@author: Locyk
 *@time: 2025/12/31
 *
 */
public interface DataStorage {
    //初始化连接
    void connect();
    //执行业务操作
    void execute(String param);
    // 获取当前存储类型（用于切换）
    String getType();
}
