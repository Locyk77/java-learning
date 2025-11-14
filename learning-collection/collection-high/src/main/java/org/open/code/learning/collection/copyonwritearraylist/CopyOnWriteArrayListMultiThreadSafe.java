package org.open.code.learning.collection.copyonwritearraylist;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList 多线程安全演示
 * 以下代码和ArrayListMultiThreadError一样，只不过使用CopyOnWriteArrayList替换了ArrayList
 *
 *@author: Locyk
 *@time: 2025/11/14
 *
 */
public class CopyOnWriteArrayListMultiThreadSafe {
    private static final List<String> list = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        // 3个写线程：各添加1000个元素
        for (int i = 0; i < 3; i++) {
            int threadId = i;
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    list.add("线程" + threadId + "-元素" + j);
                }
                System.out.println("写线程" + threadId + "完成");
            }).start();
        }

        // 10个读线程：遍历集合（大概率抛异常）
        for (int i = 0; i < 10; i++) {
            int threadId = i;
            new Thread(() -> {
                try {
                    // 循环遍历，模拟高频读
                    while (true) {
                        for (String elem : list) {
                            // 读操作无意义，仅模拟遍历
                        }
                        // 当集合大小达到3000（所有写线程完成），退出
                        if (list.size() >= 3000) {
                            System.out.println("读线程" + threadId + "正常退出，集合大小：" + list.size());
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("读线程" + threadId + "异常：" + e.getClass().getSimpleName());
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
