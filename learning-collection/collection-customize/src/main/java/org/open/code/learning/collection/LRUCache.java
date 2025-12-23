package org.open.code.learning.collection;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义 LRU 缓存：继承 LinkedHashMap 并重写淘汰规则
 *
 *@author: Locyk
 *@time: 2025/11/20
 *
 */
public class LRUCache<K,V> extends LinkedHashMap<K,V> {
    private final int MAX_CAPACITY; //缓存最大容量

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true); // 第三个参数为 true 表示按照访问顺序进行排序 是关键
        this.MAX_CAPACITY = capacity;
    }

    /**
     * 核心：重写淘汰策略--当元素个数超过容量时，删除最久未访问的元素（尾部）
     * @param eldest The least recently inserted entry in the map, or if
     *           this is an access-ordered map, the least recently accessed
     *           entry.  This is the entry that will be removed it this
     *           method returns {@code true}.  If the map was empty prior
     *           to the {@code put} or {@code putAll} invocation resulting
     *           in this invocation, this will be the entry that was just
     *           inserted; in other words, if the map contains a single
     *           entry, the eldest entry is also the newest.
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > MAX_CAPACITY;
    }

    public static void main(String[] args) {
        LRUCache<Integer, String> lru = new LRUCache<>(3); // 容量3
        // 测试添加3个元素
        lru.put(1, "A");
        lru.put(2, "B");
        lru.put(3, "C");
        System.out.println("添加3个元素后，size=" + lru.size() + "：" + lru.get(1) + "," + lru.get(2) + "," + lru.get(3)); // A,B,C

        // 测试访问元素后移至队首
        lru.get(2); // B移至队首
        System.out.println(lru.get(0));
        lru.put(4, "D"); // 容量满，删除队尾最少使用的1（A）
        System.out.println(lru.get(0));
        System.out.println("添加4后，size=" + lru.size() + "：" + lru.get(1) + "," + lru.get(2) + "," + lru.get(3) + "," + lru.get(4)); // null,B,C,D

        // 测试更新已存在的键
        lru.put(3, "C-Updated");
        System.out.println("更新3后，get(3)：" + lru.get(3)); // C-Updated

        // 测试容量为0的异常
        try {
            new MyLRUCache<>(0);
        } catch (IllegalArgumentException e) {
            System.out.println("预期异常：" + e.getMessage());
        }
    }
}
