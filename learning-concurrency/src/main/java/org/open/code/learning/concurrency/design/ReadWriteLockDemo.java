package org.open.code.learning.concurrency.design;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 *
 *@author: Locyk
 *@time: 2025/12/29
 *
 */
public class ReadWriteLockDemo {
    // 缓存容器
    private final Map<String, String> cache = new HashMap<>();
    // 读写锁
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    // 读锁
    private final java.util.concurrent.locks.Lock readLock = rwLock.readLock();
    // 写锁
    private final java.util.concurrent.locks.Lock writeLock = rwLock.writeLock();

    private static final int READER_COUNT = 5;
    private static final int WRITER_COUNT = 2;

    private static final ExecutorService POOL = Executors.newFixedThreadPool(READER_COUNT+WRITER_COUNT);

    // 从缓存读取数据（读操作）
    public String get(String key) {
        readLock.lock(); // 获取读锁（共享）
        try {
            System.out.printf("[%s] 读取缓存：key=%s%n", Thread.currentThread().getName(), key);
            // 模拟读操作耗时
            Thread.sleep(100);
            return cache.get(key);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            return null;
        } finally {
            readLock.unlock(); // 释放读锁
        }
    }

    // 向缓存写入数据（写操作）
    public void put(String key, String value) {
        writeLock.lock(); // 获取写锁（独占）
        try {
            System.out.printf("[%s] 写入缓存：key=%s, value=%s%n", Thread.currentThread().getName(), key, value);
            // 模拟写操作耗时
            Thread.sleep(500);
            cache.put(key, value);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            writeLock.unlock(); // 释放写锁
        }
    }

    public static void main(String[] args) throws InterruptedException{
        ReadWriteLockDemo cache = new ReadWriteLockDemo();
        for (int i = 0; i < READER_COUNT; i++){
            int finalI = i;
            POOL.execute(() -> cache.get("user_"+finalI));
        }
        for (int j = 0; j < WRITER_COUNT; j++){
            int finalJ = j;
            POOL.execute(() -> cache.put("user_"+finalJ, "user_"+finalJ));
        }



        Thread.sleep(2000);
        POOL.shutdownNow();
    }




}
