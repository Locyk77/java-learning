package org.open.code.learning.concurrency.condition;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者-消费者（无限循环，线程池发出中断信号终止循环，具体可看main方法注释）
 *@author: Locyk
 *@time: 2025/12/4
 *
 */
public class FairLockInterruptExample {

    private static final Lock lock = new ReentrantLock(true); // 公平锁
    private static final Condition notEmpty = lock.newCondition(); //条件变量--队列空时，等待；队列非空时，唤醒
    private static final Condition notFull = lock.newCondition(); // 条件变量--队列满时，等待；队列非满时，唤醒
    private static final Queue<Integer> queue = new LinkedList<>(); // 队列
    private static final int capacity = 5; // 队列容量
    // 线程数
    private static final int THREAD_COUNT = 10;

    /**
     * 生产者
     * @param value
     * @throws InterruptedException
     */
    public static void produce(int value) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (queue.size() == capacity) {
                notFull.await(); // 队列满时，生产者等待
            }
            queue.add(value);
            System.out.println("生产者-生产：" + value);
            notEmpty.signal(); // 唤醒等待的消费者
        } finally {
            lock.unlock();
        }
    }

    /**
     * 消费者
     * @return
     * @throws InterruptedException
     */
    public static int consume() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (queue.isEmpty()) {
                notEmpty.await(); // 队列空时，消费者等待
            }
            int value = queue.poll();
            System.out.println("消费者-消费：" + value);
            notFull.signal(); // 唤醒等待的生产者
            return value;
        } finally {
            lock.unlock();
        }
    }

    /**
     *
     * 循环体无可中断方法（如sleep()/await()）：线程池调用shutdownNow()时，会直接设置线程的中断标志位。循环条件!Thread.currentThread().isInterrupted()可检测到标志位为true，无需额外处理，线程会直接退出循环，关闭线程池即可终止任务。
     * 循环体包含可中断方法（如sleep()/await()）：线程池shutdownNow()触发中断时，若线程正处于可中断方法中，会抛出InterruptedException，且 JVM 会自动清除中断标志位（标志位变为false）。此时需在catch块中调用Thread.currentThread().interrupt()恢复中断标志位，否则：
     * 若异常被  循环外层try-catch 捕获，线程会直接跳出循环（不会持续运行）；
     * 若异常被 循环内层try-catch 捕获，循环条件会因标志位丢失误判为false，导致线程继续运行。
     * 恢复标志位既能保证异常场景下循环正确终止，也符合并发编程语义规范，避免代码扩展后出现线程无法终止的问题。
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        int j = ThreadLocalRandom.current().nextInt(100) + 1;
                        produce(j);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 恢复中断状态
                    System.out.println("生产者线程中断：" + Thread.currentThread().getName());
                }
            });
            executor.submit(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        consume();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 恢复中断状态
                    System.out.println("消费者线程中断：" + Thread.currentThread().getName());
                }
            });
        }
        Thread.sleep(6000);
        executor.shutdownNow();
        if (executor.awaitTermination(1, TimeUnit.MINUTES)) {
            System.out.println("线程池已正常关闭");
        } else {
            System.out.println("线程池关闭超时");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("总操作耗时：" + (endTime - startTime) + "ms");
    }
}
