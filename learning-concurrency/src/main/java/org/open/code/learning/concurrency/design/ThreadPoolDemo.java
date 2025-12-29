package org.open.code.learning.concurrency.design;

import java.util.concurrent.*;

/**
 * 自定义线程池（Spring环境下，推荐用Spring的线程池）
 *@author: Locyk
 *@time: 2025/12/29
 *
 */
public class ThreadPoolDemo {
    private static final int PRODUCER_COUNT = 2;
    private static final int CONSUMER_COUNT = 3;
    private static final ExecutorService POOL = new ThreadPoolExecutor(
            2, // 核心线程数（常驻线程）
            PRODUCER_COUNT + CONSUMER_COUNT, // 最大线程数（核心线程忙且队列满时，最多创建5个线程）
            60L, TimeUnit.SECONDS, // 非核心线程空闲60秒后销毁
            new LinkedBlockingQueue<>(100),// 任务队列（容量100）
            new ThreadFactory() { // 自定义线程命名
                private int count = 1;
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "自定义线程-" + count++);
                }
            },
            new ThreadPoolExecutor.CallerRunsPolicy()// 拒绝策略（队列满时，由提交任务的线程执行）
    );
    private static final BlockingQueue<String> ORDER_QUEUE = new ArrayBlockingQueue<>(10);

    private static void produce(String producerName) {
        try {
            int count = 0;
            while (!Thread.currentThread().isInterrupted()) {
                String orderId = "ORDER_" + (++count);
                // 生产订单：队列满时自动阻塞，直到有空间
                ORDER_QUEUE.put(orderId);
                System.out.printf("[%s] 生产订单：%s，当前队列大小：%d%n",
                        producerName, orderId, ORDER_QUEUE.size());
                // 模拟生产耗时
                TimeUnit.MILLISECONDS.sleep(500);
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            System.err.println(producerName + "被中断，停止生产");
        }
    }

    private static void consume(String consumerName) {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // 消费订单：队列空时自动阻塞，直到有订单
                String orderId = ORDER_QUEUE.take();
                System.out.printf("[%s] 消费订单：%s，当前队列大小：%d%n",
                        consumerName, orderId, ORDER_QUEUE.size());
                // 模拟处理耗时（比生产慢，会导致队列堆积）
                TimeUnit.MILLISECONDS.sleep(800);
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            System.err.println(consumerName + "被中断，停止消费");
        }
    }

    /**
     * 此处代码执行和【ProducerConsumerDemo】的执行效果不同，是因为线程池设置的核心线程只有2个，如果把核心线程数设置为5，则结果和【ProducerConsumerDemo】一致
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException{
        POOL.execute(() -> produce("生产者1"));
        POOL.execute(() -> produce("生产者2"));

        POOL.execute(() -> consume("消费者1"));
        POOL.execute(() -> consume("消费者2"));
        POOL.execute(() -> consume("消费者3"));

        Thread.sleep(6000);
        POOL.shutdownNow();
    }




}
