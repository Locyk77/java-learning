package org.open.code.learning.collection;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 *@author: Locyk
 *@time: 2025/12/3
 *
 */
public class ConcurrentCollectionTest {
    // 原子计数器：统计操作成功数
    private static final AtomicInteger successCount = new AtomicInteger(0);
    // 测试次数：每个线程执行10000次操作
    private static final int OP_COUNT_PER_THREAD = 2000;
    // 线程数：100个并发线程
    private static final int THREAD_COUNT = 100;

    public static void main(String[] args) throws InterruptedException {

        // 1. 测试非线程安全集合：ArrayList
        System.out.println("===== 测试 ArrayList（非线程安全） =====");
        testCollection(new ArrayList<>());

        // 2. 测试传统线程安全集合：Vector
        System.out.println("\n===== 测试 Vector（全局锁） =====");
        testCollection(new Vector<>());

        // 3. 测试JUC并发集合：CopyOnWriteArrayList
        System.out.println("\n===== 测试 CopyOnWriteArrayList（写时复制） =====");
        testCollection(new CopyOnWriteArrayList<>());

        // 4. 测试JUC并发集合：ConcurrentLinkedQueue
        System.out.println("\n===== 测试 ConcurrentLinkedQueue（无锁队列） =====");
        testCollection(new ConcurrentLinkedQueue<>());
    }

    private static void testCollection(Collection<Integer> collection) throws InterruptedException {
        successCount.set(0); // 重置计数器
        long startTime = System.currentTimeMillis();

        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                for (int j = 0; j < OP_COUNT_PER_THREAD; j++) {
                    // 并发写：添加元素
                    collection.add(j);
                    // 并发读：随机查询（仅演示，实际可加判断）
                    if (collection.contains(j)) {
                        successCount.incrementAndGet();
                    }
                }
            });
        }

        // 关闭线程池并等待完成
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        long endTime = System.currentTimeMillis();
        // 输出结果
        System.out.println("总操作耗时：" + (endTime - startTime) + "ms");
        System.out.println("成功操作数：" + successCount.get());
        System.out.println("集合最终大小：" + collection.size());
    }

}
