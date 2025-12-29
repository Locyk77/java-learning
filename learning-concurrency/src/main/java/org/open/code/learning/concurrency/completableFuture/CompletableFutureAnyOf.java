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
public class CompletableFutureAnyOf {
    private static final ExecutorService POOL = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws Exception {
        // 任务1：查询淘宝价格（慢）
        CompletableFuture<String> taobaoFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "淘宝价格：99.9";
        }, POOL);

        // 任务2：查询京东价格（快）
        CompletableFuture<String> jdFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "京东价格：89.9";
        }, POOL);

        // 任务3：查询拼多多价格（中）
        CompletableFuture<String> pddFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "拼多多价格：79.9";
        }, POOL);

        // 组合：取第一个完成的任务
        CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(taobaoFuture, jdFuture, pddFuture);

        // 获取最快的结果
        String fastestResult = (String) anyFuture.get();
        System.out.println("最快的价格结果：" + fastestResult); // 输出：京东价格：89.9


        POOL.shutdown();
    }
}
