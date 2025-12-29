package org.open.code.learning.concurrency.fork;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

/**
 * Fork/Join 测试类
 *
 *@author: Locyk
 *@time: 2025/12/23
 *
 */
public class ForkJoinDemo {

    public static void main(String[] args) {


        // 3. 创建ForkJoin线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int start = 1;
        int end = 10000;

        // 4. 提交大任务
        long startTime = System.currentTimeMillis();
        SumTask task = new SumTask(start, end);
        Long result = forkJoinPool.invoke(task);
        long endTime = System.currentTimeMillis();

        // 5. 输出结果
        System.out.println("1~10000的和：" + result); // 输出50005000
        System.out.println("Fork/Join耗时：" + (endTime - startTime) + "ms");

        long singleSum = 0;
        for(int i = start; i <= end; i++){
            singleSum += i;
        }
        endTime = System.currentTimeMillis();
        System.out.println("单线程总和：" + singleSum);
        System.out.println("单线程耗时：" + (endTime - startTime) + "ms");

        // 6. 关闭线程池
        forkJoinPool.shutdown();



        // 配置：待处理目录、目标字符串、替换字符串
        File rootDir = new File("J:\\test"); // 替换为你的实际目录路径
        String targetStr = "oldStr";
        String replaceStr = "newStr";

        // 校验目录是否存在
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            System.err.println("错误：目录不存在或不是有效目录 → " + rootDir.getAbsolutePath());
            return;
        }

        // 执行并行替换任务
        ForkJoinPool forkJoinPool_ = new ForkJoinPool(); // 默认使用CPU核心数作为线程数
        try {
            long startTime_ = System.currentTimeMillis();
            FileContentReplaceTask task_ = new FileContentReplaceTask(rootDir, targetStr, replaceStr);
            forkJoinPool_.invoke(task_); // 提交任务并等待完成
            long costTime = System.currentTimeMillis() - startTime_;

            System.out.println("\n===== 处理完成 =====");
            System.out.println("总耗时：" + costTime + " 毫秒");
            System.out.println("处理目录：" + rootDir.getAbsolutePath());
        } finally {
            forkJoinPool_.shutdown(); // 关闭线程池
        }


    }
}
