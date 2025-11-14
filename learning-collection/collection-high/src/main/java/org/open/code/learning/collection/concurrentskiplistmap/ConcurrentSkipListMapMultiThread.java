package org.open.code.learning.collection.concurrentskiplistmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.DoubleToIntFunction;

/**
 *
 *@author: Locyk
 *@time: 2025/11/14
 *
 */
public class ConcurrentSkipListMapMultiThread {
    private static final ConcurrentSkipListMap<Long, String> sortedMap = new ConcurrentSkipListMap<>();

    public static void main(String[] args) {
        // 5个写线程：添加时间戳为key的数据（保证有序）
        for (int i = 0; i < 5; i++) {
            int threadId = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < 3; j++) {
                        // 时间戳作为key（天然递增，保证有序）
                        long timestamp = System.currentTimeMillis();
                        long uniqueKey = timestamp * 1000 + threadId * 10 + j;
                        String value = "线程" + threadId + "-数据" + j;
                        sortedMap.put(uniqueKey, value);
                        System.out.println("添加：" + uniqueKey + "=" + value);

                        // 可选：微小延迟，让key的时间戳部分有差异，有序性更直观
                        Thread.sleep(1);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("写线程" + threadId + "被中断：" + e.getMessage());
                }

            }).start();
        }

        // 2个读线程：有序遍历+范围查询
        for (int i = 0; i < 2; i++) {
            int threadId = i;
            new Thread(() -> {
                // 等待所有写线程完成（15个元素）
                while (sortedMap.size() < 15) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                    }
                }
                System.out.println("\n=== 读线程" + threadId + "有序遍历结果 ===");
                // 遍历：按key升序排列（天然有序）
                sortedMap.forEach((key, value) -> {
                    System.out.println(key + "=" + value);
                });

                // 范围查询：获取所有key大于“最小key+1”的元素（线程安全）
                Long minKey = sortedMap.firstKey();
                Map<Long, String> subMap = sortedMap.tailMap(minKey + 1);
                System.out.println("=== 读线程" + threadId + "范围查询（key>" + (minKey + 1) + "）===");
                subMap.forEach((key, value) -> System.out.println(key + "=" + value));
            }).start();
        }

    }


}
