package org.open.code.learning.concurrency.cas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 展示 CAS（无阻塞）和锁（阻塞）的核心差异
 *
 *@author: Locyk
 *@time: 2025/12/4
 *
 */
public class BlockVsNonBlockExample {
    // 示例共用变量
    private static final int THREAD_NUM = 5; // 线程数
    private static final  int TASK_NUM = 3; // 每个线程执行的次数

    // 锁阻塞示例用到的变量
    private static final ReentrantLock lock = new ReentrantLock();
    private static int count = 0;
    // CAS无阻塞示例用到的变量
    private static final AtomicInteger casCount = new AtomicInteger(0);

    // -------------- 1. 锁（阻塞）示例 --------------
    public static void lock() {
        lock.lock();
        try {
            // 模拟耗时操作（放大阻塞效果）
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            System.out.printf("[锁-线程%s] 自增成功，count=%d（线程状态：%s）%n",
                    Thread.currentThread().getName(), count, Thread.currentThread().getState());
        } finally {
            lock.unlock();
        }
    }

    public static int getLockCount() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    // -------------- 2. CAS（无阻塞）示例 --------------
    public static void cas() {
        int expect;
        int newVal;
        // 自旋重试：CAS失败则循环，线程始终处于运行态
        do {
            expect = casCount.get();
            newVal = expect + 1;
            // 打印重试过程（体现“无阻塞自旋”）
            System.out.printf("[CAS-线程%s] 尝试CAS：expect=%d, newVal=%d（线程状态：%s）%n",
                    Thread.currentThread().getName(), expect, newVal, Thread.currentThread().getState());
            // 模拟耗时操作（放大自旋效果）
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!casCount.compareAndSet(expect, newVal)); // CAS失败则自旋

        System.out.printf("[CAS-线程%s] CAS成功，count=%d%n",
                Thread.currentThread().getName(), newVal);
    }

    public static int getCasCount() {
        return casCount.get();
    }


    public static void main(String[] args) throws InterruptedException{
        // ========== 测试1：锁（阻塞） ==========
        System.out.println("===== 开始测试【锁-阻塞】=====");
        ExecutorService lockExecutor = Executors.newFixedThreadPool(THREAD_NUM);
        for (int i = 0; i < THREAD_NUM; i++) {
            int threadId = i;
            lockExecutor.submit(() -> {
                for (int j = 0; j < TASK_NUM; j++) {
                    lock();
                }
            });
        }
        lockExecutor.shutdown();
        lockExecutor.awaitTermination(1, TimeUnit.MINUTES);
        System.out.printf("【锁-阻塞】最终count=%d%n%n", getLockCount());

        // ========== 测试2：CAS（无阻塞） ==========
        System.out.println("===== 开始测试【CAS-无阻塞】=====");
        ExecutorService casExecutor = Executors.newFixedThreadPool(THREAD_NUM);
        for (int i = 0; i < THREAD_NUM; i++) {
            int threadId = i;
            casExecutor.submit(() -> {
                for (int j = 0; j < TASK_NUM; j++) {
                    cas();
                }
            });
        }
        casExecutor.shutdown();
        casExecutor.awaitTermination(1, TimeUnit.MINUTES);
        System.out.printf("【CAS-无阻塞】最终count=%d%n", getCasCount());
    }
}
