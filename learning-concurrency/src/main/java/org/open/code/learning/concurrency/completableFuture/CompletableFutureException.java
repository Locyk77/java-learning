package org.open.code.learning.concurrency.completableFuture;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 *
 *@author: Locyk
 *@time: 2025/12/25
 *
 */
public class CompletableFutureException {
    private static final ExecutorService POOL = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws Exception {
        CompletableFuture<String> future_1 = CompletableFuture.supplyAsync(() -> {
                    // 模拟异常
                    if (true) {
                        throw new RuntimeException("查询失败");
                    }
                    return "正常结果";
                }, POOL)
                // 异常时返回默认值
                .exceptionally(e -> {
                    System.err.println("异常信息：" + e.getMessage());
                    return "默认结果";
                });

        String result_1 = future_1.get();
        System.out.println("最终结果1：" + result_1); // 输出：默认结果


        CompletableFuture<String> future_2 = CompletableFuture.supplyAsync(() -> {
                    if (true) {
                        throw new RuntimeException("查询失败");
                    }
                    return "正常结果";
                }, POOL)// handle：同时处理正常和异常情况
                .handle((result, e) -> {
                    if (e != null) {
                        System.err.println("异常：" + e.getMessage());
                        return "默认结果";
                    } else {
                        return result + "（处理后）";
                    }
                });

        String result_2 = future_2.get();
        System.out.println("最终结果2：" + result_2); // 输出：默认结果

        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> {
                    // 生产结果（比如查询数据库）
                    return "商品价格：99.9";
                }, POOL)
                .whenComplete((result, e) -> {
                    // 消费结果（比如打印日志）
                    if (e != null) {
                        System.err.println("查询异常：" + e.getMessage());
                    } else {
                        System.out.println("查询结果：" + result);
                    }
                })
                // 后续还要使用结果（比如计算最终价格）
                .thenApply(result -> {
                    BigDecimal price = new BigDecimal(result.replace("商品价格：", ""));
                    return price.add(new BigDecimal("10")).toString();
                });

        // 获取最终结果
        String finalResult = future.get();
        System.out.println("最终价格：" + finalResult); // 输出：109.9

        POOL.shutdown();
    }
}
