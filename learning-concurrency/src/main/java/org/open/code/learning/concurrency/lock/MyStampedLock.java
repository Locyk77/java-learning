package org.open.code.learning.concurrency.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.StampedLock;

/**
 * 利用StampedLock实现HashMap的线程安全（有点类似于ReentrantReadWriteLock）
 *
 * 注意事项：
 * 1. 不可重入：StampedLock 不支持重入，同一线程多次获取锁会导致死锁；
 * 2. 条件变量缺失：无 Condition 支持，需手动实现线程通信；
 * 3. 中断处理：悲观读 / 写锁的中断需通过readLockInterruptibly()/writeLockInterruptibly()。
 *@author: Locyk
 *@time: 2025/12/4
 *
 */
public class MyStampedLock {
    private static final Map<String,Integer> cache = new HashMap<>();
    private static final StampedLock lock = new StampedLock();
    private static final AtomicInteger successCount = new AtomicInteger(0); // 原子计数器：统计操作成功数
    // 线程数
    private static final int THREAD_COUNT = 10;

    /**
     * 乐观读：无锁读取数据，通过版本戳验证数据是否被修改（冲突概率低时性能极高）；
     * 悲观读：类似 ReentrantReadWriteLock 的读锁（共享锁）；
     * @param key
     * @return
     */
    public static Integer get(String key) {
        // 1. 乐观读（无锁）
        long stamp = lock.tryOptimisticRead(); // 获取乐观读锁
        Integer value = cache.get(key);
        // 2. 验证版本戳（数据是否被修改）
        if (!lock.validate(stamp)) { // 验证锁
            // 3. 乐观读失败，升级为悲观读
            stamp = lock.readLock(); // 获取读锁
            try {
                value = cache.get(key);
            } finally {
                lock.unlockRead(stamp); // 释放读锁
            }
        }
        return value;
    }

    /**
     * 写锁：独占锁，获取时返回版本戳，释放时需匹配戳。
     * @param key
     * @param value
     */
    public static void put(String key, Integer value) {
        // 1. 获取写锁
        long stamp = lock.writeLock();
        try {
            cache.put(key, value);
        } finally {
            lock.unlockWrite(stamp); // 释放写锁
        }
    }

    public static void main(String[] args) throws InterruptedException{
        successCount.set(0); // 重置成功数
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 1000; j++) {
                    String key = Thread.currentThread().getName() + j;
                    int value = ThreadLocalRandom.current().nextInt(100) + 1;
                    put(key, value);
                    Integer result = get(key);
                    successCount.incrementAndGet();
                    System.out.println("线程" + Thread.currentThread().getName() + "获取的缓存结果为：" + result);
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        long endTime = System.currentTimeMillis();
        System.out.println("成功数：" + successCount.get());
        System.out.println("总缓存数：" + cache.size());
        System.out.println("总操作耗时：" + (endTime - startTime) + "ms");
    }
}
