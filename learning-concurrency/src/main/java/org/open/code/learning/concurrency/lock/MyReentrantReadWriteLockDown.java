package org.open.code.learning.concurrency.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 利用ReentrantReadWriteLock的写锁降级为读锁，写入后读取 验证/其他操作，保证原子性
 *@author: Locyk
 *@time: 2025/12/4
 *
 */
public class MyReentrantReadWriteLockDown {
    private static final Map<String, Long> cache = new HashMap<>();
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(); // 读写锁
    private static final Lock readLock = lock.readLock(); // 读锁
    private static final Lock writeLock = lock.writeLock(); // 写锁
    // 线程数
    private static final int THREAD_COUNT = 10;
    public static long calculateOrderAmount(String orderId) {
        writeLock.lock();
        long totalAmount = 0L;
        try {
            // 1. 写锁下执行计算+写入（独占，避免并发计算冲突）
            totalAmount = mockCalculateOrderAmount(orderId);
            cache.put(orderId, totalAmount);
            System.out.println(Thread.currentThread().getName() + " 计算并写入订单金额：" + orderId + "=" + totalAmount);

            //2. 降级：持有写锁时获取读锁
            readLock.lock();
        } finally {
            writeLock.unlock();
        }

        try{
            long result = cache.get(orderId);
            System.out.println(Thread.currentThread().getName() + " 返回订单金额：" + orderId + "=" + result);
            return result;
        }finally {
            readLock.unlock();
        }
    }

    private static long mockCalculateOrderAmount(String orderId) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return ThreadLocalRandom.current().nextLong(1000, 10000);
    }

    public static void main(String[] args) throws InterruptedException{
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {

                String orderId = Thread.currentThread().getName() + ThreadLocalRandom.current().nextInt(100);
                long result = calculateOrderAmount(orderId);
                System.out.println("线程" + Thread.currentThread().getName() + "获取的返回订单结果为：" + result);
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        long endTime = System.currentTimeMillis();
        System.out.println("总操作耗时：" + (endTime - startTime) + "ms");
    }
}
