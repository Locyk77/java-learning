package org.open.code.learning.concurrency.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 利用ReentrantReadWriteLock实现HashMap的线程安全（有点类似于StampedLock）
 *@author: Locyk
 *@time: 2025/12/4
 *
 */
public class MyReentrantReadWriteLock {

    private static final Map<String, Integer> cache = new HashMap<>();
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(); // 读写锁
    private static final Lock readLock = lock.readLock(); // 读锁
    private static final Lock writeLock = lock.writeLock(); // 写锁
    private static final AtomicInteger successCount = new AtomicInteger(0); // 原子计数器：统计操作成功数
    // 线程数
    private static final int THREAD_COUNT = 10;

    /**
     * 读操作（共享）
     * @param key
     * @return
     */
    public static Integer get(String key) {
        readLock.lock(); // 获取读锁
        try {
            return cache.get(key);
        } finally {
            readLock.unlock(); // 释放读锁
        }
    }

    /**
     * 写操作（独占）
     * @param key
     * @param value
     */
    public static void put(String key, Integer value) {
        writeLock.lock(); // 获取写锁
        try {
            cache.put(key, value);
        } finally {
            writeLock.unlock(); // 释放写锁
        }
    }

    /**
     * 清空缓存（独占）
     */
    public static void clear() {
        writeLock.lock();
        try {
            cache.clear();
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 读锁共享：多个线程可同时获取读锁（无写锁时）
     * 写锁独占：写锁获取时，所有读锁 / 写锁需释放；
     * 读写互斥：读锁持有期间，写锁需等待；写锁持有期间，读锁需等待。
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
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
