package org.open.code.learning.concurrency.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 *@author: Locyk
 *@time: 2025/12/25
 *
 */
public class CompletableFutureBasic {
    // 自定义线程池（推荐，避免使用默认的ForkJoinPool.commonPool()）
    private static final ExecutorService CUSTOM_POOL = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws Exception {
        // 1. 无返回值异步任务
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("异步任务1执行完成（无返回值）");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, CUSTOM_POOL); // 指定自定义线程池（关键！）

        // 等待任务完成（主线程阻塞，仅演示）
        future1.get();
        System.out.println("主线程：任务1已完成");

        // 2. 有返回值异步任务
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                return "异步任务2的返回结果";
            } catch (InterruptedException e) {
                throw new RuntimeException("任务2被中断", e);
            }
        }, CUSTOM_POOL);

        // 获取返回结果（get()会阻塞，也可用非阻塞方式）
        String result2 = future2.get();
        System.out.println("主线程：任务2返回结果 = " + result2);

        // 3. 手动完成任务（无需异步执行）
        CompletableFuture<String> future3 = new CompletableFuture<>();
        // 手动设置结果（完成任务）
        future3.complete("手动设置的结果");
        // 获取结果（无阻塞）
        String result3 = future3.get();
        System.out.println("主线程：任务3返回结果 = " + result3);

        CUSTOM_POOL.shutdown();
    }
}
