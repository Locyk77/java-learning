package org.open.code.learning.jvm.plugins.loader;

/**
 *
 *@author: Locyk
 *@time: 2025/12/30
 *
 */
public interface Plugin {
    void execute();
    String getName(); // 插件名称
}
