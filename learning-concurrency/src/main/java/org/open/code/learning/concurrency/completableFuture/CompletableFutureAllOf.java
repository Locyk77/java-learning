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
public class CompletableFutureAllOf {
    private static final ExecutorService POOL = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws Exception {
        // 任务1：查询用户信息
        CompletableFuture<String> userFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "用户信息：ID=1001, 姓名=张三";
        }, POOL);

        // 任务2：查询订单信息
        CompletableFuture<String> orderFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "订单信息：ID=2001, 金额=199.9";
        }, POOL);

        // 任务3：查询物流信息
        CompletableFuture<String> logisticsFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "物流信息：ID=3001, 状态=已发货";
        }, POOL);

        // 组合：等待所有任务完成
        CompletableFuture<Void> allFuture = CompletableFuture.allOf(userFuture, orderFuture, logisticsFuture);

        // 所有任务完成后，获取各自结果
        allFuture.thenRunAsync(() -> {
            try {
                String user = userFuture.get();
                String order = orderFuture.get();
                String logistics = logisticsFuture.get();
                System.out.println("所有任务完成，合并结果：\n" + user + "\n" + order + "\n" + logistics);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, POOL);
    }

}
