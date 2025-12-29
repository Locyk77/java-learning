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
 * 生产者-消费者（模拟真实环境生产者、消费者所需执行时间）
 *@author: Locyk
 *@time: 2025/12/4
 *
 */
public class FairLockExample {

    private static final Lock lock = new ReentrantLock(true); // 公平锁
    private static final Condition notEmpty = lock.newCondition(); // 条件变量--队列空时，等待；队列非空时，唤醒
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
        lock.lock();
        try {
            while (queue.size() == capacity) {
                notFull.await(); // 队列满时，生产者等待
            }
            Thread.sleep(1000);
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
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await(); // 队列空时，消费者等待
            }
            Thread.sleep(500);
            int value = queue.poll();
            System.out.println("消费者-消费：" + value);
            notFull.signal(); // 唤醒等待的生产者
            return value;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                try {
                    int j = ThreadLocalRandom.current().nextInt(100) + 1;
                    produce(j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            executor.submit(() -> {
                try {
                    consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        long endTime = System.currentTimeMillis();
        System.out.println("总操作耗时：" + (endTime - startTime) + "ms");
    }
}
