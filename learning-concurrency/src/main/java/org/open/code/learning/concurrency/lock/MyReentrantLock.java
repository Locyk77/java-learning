package org.open.code.learning.concurrency.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 利用ReentrantLock实现ArrayList的线程安全
 *
 *@author: Locyk
 *@time: 2025/12/4
 *
 */
public class MyReentrantLock {
    private static final Lock lock = new ReentrantLock();// 可重入锁、默认非公平锁
    // 线程数
    private static final int THREAD_COUNT = 5;
    // 每个线程操作的次数
    private static final int OP_COUNT_PER_THREAD = 1000;

    private static final List<Integer> list = new ArrayList<>(THREAD_COUNT * OP_COUNT_PER_THREAD);

    /**
     * 写入ArrayList
     */
    public static void writeArrayList() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                for (int j = 0; j < OP_COUNT_PER_THREAD; j++) {
                    try {
                        lock.lock(); //获取锁
                        list.add(j);
                    } finally {
                        lock.unlock(); //释放锁（必须在finally中）
                    }
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        long endTime = System.currentTimeMillis();
        System.out.println("总操作耗时：" + (endTime - startTime) + "ms");
        System.out.println("集合计划大小：" + THREAD_COUNT * OP_COUNT_PER_THREAD);
        System.out.println("集合最终大小：" + list.size());
    }

    public static void main(String[] args) throws InterruptedException {
        writeArrayList();
    }
}
