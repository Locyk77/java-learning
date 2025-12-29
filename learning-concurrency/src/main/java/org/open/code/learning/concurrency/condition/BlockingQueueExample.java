package org.open.code.learning.concurrency.condition;

import java.util.concurrent.*;

/**
 * 生产者-消费者（使用阻塞队列完成）
 *@author: Locyk
 *@time: 2025/12/4
 *
 */
public class BlockingQueueExample {
    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5); // 阻塞队列
    private static final int THREAD_COUNT = 10; // 线程数

    public static void produce(int value) throws InterruptedException {
        // 模拟生产者执行耗时（与原逻辑一致）
        Thread.sleep(1000);
        queue.put(value);
        System.out.println("生产者-生产：" + value);
    }

    public static int consume() throws InterruptedException {
        Thread.sleep(500);
        int value = queue.take();
        // 模拟消费者执行耗时（与原逻辑一致）
        System.out.println("消费者-消费：" + value);
        return value;
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
