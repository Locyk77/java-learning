package org.open.code.learning.jvm.plugins.jdbc;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * JDBC驱动管理器
 *
 *@author: Locyk
 *@time: 2025/12/31
 *
 */
public class StorageManager {
    // 缓存所有加载的实现类
    private static final Map<String, DataStorage> STORAGE_MAP = new HashMap<>();

    // 静态代码块：启动时自动扫描加载所有实现类
    static {
        ServiceLoader<DataStorage> loader = ServiceLoader.load(DataStorage.class);
        for (DataStorage storage : loader) {
            // 按 type 缓存，用于快速切换
            STORAGE_MAP.put(storage.getType(), storage);
        }
    }

    // 对外提供：根据 type 获取指定实现
    public static DataStorage getStorage(String type) {
        DataStorage storage = STORAGE_MAP.get(type.toUpperCase());
        if (storage == null) {
            throw new IllegalArgumentException("不支持的存储类型：" + type);
        }
        return storage;
    }

    // 对外提供：获取所有实现类（用于扩展）
    public static Set<String> getAllStorageTypes() {
        return STORAGE_MAP.keySet();
    }
}

