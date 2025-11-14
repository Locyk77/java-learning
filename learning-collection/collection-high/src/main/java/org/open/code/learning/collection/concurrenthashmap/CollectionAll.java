package org.open.code.learning.collection.concurrenthashmap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 *
 *@author: Locyk
 *@time: 2025/11/12
 *
 */
public class CollectionAll {
    private static final ConcurrentHashMap<String, User> userCache = new ConcurrentHashMap<>();


    public static void main(String[] args) {

        // 目标用户（假设模拟该用户的100次登录请求）
        User targetUser = new User("Locyk");
        User targetUser1 = new User("CuiC");
        UserLoginTask loginTask1 = new UserLoginTask(userCache, targetUser1);
        CompletableFuture.runAsync(loginTask1);
        // 存储所有异步任务，用于后续等待全部完成
        List<CompletableFuture<Void>> futureList = new ArrayList<>(20);

        // 创建100个异步任务（模拟100次并发登录）
        for (int i = 0; i < 20; i++) {
            // 每次循环创建独立的Runnable实例（避免共享状态问题）
            UserLoginTask loginTask = new UserLoginTask(userCache, targetUser);
            // 提交异步任务并加入列表
            CompletableFuture<Void> future = CompletableFuture.runAsync(loginTask);
            futureList.add(future);
        }

        try {
            // 等待所有异步任务完成（阻塞主线程）
            CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).get();
            // 所有任务执行完毕后，打印最终结果
            System.out.println("所有异步登录任务执行完成！");
            System.out.println("用户缓存最终状态：" + userCache);
            System.out.println("目标用户登录次数：" + userCache.get(targetUser.getName()).getLoginCount());
        } catch (InterruptedException e) {
            // 处理线程中断异常（如主线程被打断）
            Thread.currentThread().interrupt();
            System.err.println("主线程等待被中断：" + e.getMessage());
        } catch (ExecutionException e) {
            // 处理异步任务执行过程中的异常
            System.err.println("异步任务执行失败：" + e.getCause().getMessage());
        }
    }
}
