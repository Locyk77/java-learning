package org.open.code.learning.concurrency.completableFuture;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 *@author: Locyk
 *@time: 2025/12/25
 *
 */
public class CompletableFutureChain {
    // 自定义线程池（不同步骤可指定不同线程池，解耦）
    private static final ExecutorService IO_POOL = Executors.newFixedThreadPool(3); // IO密集型
    private static final ExecutorService CALC_POOL = Executors.newFixedThreadPool(2); // 计算密集型

    public static void main(String[] args) throws Exception {
        // 链式操作：查询商品ID→获取商品价格→计算最终价格→记录日志
        CompletableFuture<Void> future = CompletableFuture
                // 第一步：异步查询商品ID（有返回值）
                .supplyAsync(() -> {
                    System.out.println("第一步：查询商品ID | 线程：" + Thread.currentThread().getName());
                    return "goods_1001"; // 模拟从DB查询的商品ID
                }, IO_POOL)

                // 第二步：根据商品ID查价格（接收上一步结果，转换）
                .thenApplyAsync(goodsId -> {
                    System.out.println("第二步：查询商品价格 | 线程：" + Thread.currentThread().getName() + " | 商品ID：" + goodsId);
                    return new BigDecimal("99.9"); // 模拟价格
                }, IO_POOL)

                // 第三步：计算最终价格（加运费）
                .thenApplyAsync(price -> {
                    System.out.println("第三步：计算最终价格 | 线程：" + Thread.currentThread().getName() + " | 原价：" + price);
                    return price.add(new BigDecimal("10")); // 加10元运费
                }, CALC_POOL)

                // 第四步：消费最终价格（无返回值）
                .thenAcceptAsync(finalPrice -> {
                    System.out.println("第四步：消费结果 | 线程：" + Thread.currentThread().getName() + " | 最终价格：" + finalPrice);
                }, IO_POOL)

                // 第五步：执行收尾操作（无参）
                .thenRunAsync(() -> {
                    System.out.println("第五步：记录日志 | 线程：" + Thread.currentThread().getName());
                }, IO_POOL);

        // 等待链式操作完成
        future.get();
        // 关闭线程池
        IO_POOL.shutdown();
        CALC_POOL.shutdown();
    }
}
