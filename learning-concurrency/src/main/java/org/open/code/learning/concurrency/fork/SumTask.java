package org.open.code.learning.concurrency.fork;

import java.util.concurrent.RecursiveTask;

/**
 * 拆分任务计算总和
 *
 *@author: Locyk
 *@time: 2025/12/23
 *
 */
public class SumTask extends RecursiveTask<Long> {

    // 拆分阈值：每个任务最多计算100个数的和
    private static final int THRESHOLD = 100;
    private final int start;
    private final int end;

    public SumTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        // 判断是否需要拆分：如果任务足够小，直接执行
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            // 直接计算小任务
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 拆分任务（Fork）
            int mid = (start + end) / 2;
            SumTask leftTask = new SumTask(start, mid);
            SumTask rightTask = new SumTask(mid + 1, end);

            // 执行子任务（异步 Fork）
            leftTask.fork();
            rightTask.fork();

            // 合并子任务结果（Join）
            Long leftResult = leftTask.join();
            Long rightResult = rightTask.join();
            sum = leftResult + rightResult;
        }
        return sum;
    }
}
