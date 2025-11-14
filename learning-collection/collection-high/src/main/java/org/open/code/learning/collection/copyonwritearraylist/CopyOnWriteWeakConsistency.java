package org.open.code.learning.collection.copyonwritearraylist;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList 弱一致性演示
 *
 * 迭代器创建时，底层数组是 [A, B]；
 * 遍历期间线程 2 添加了 C，集合实际大小变为 3，但迭代器仍遍历快照 [A, B]；
 * 这就是 “弱一致性”—— 牺牲实时性换高并发读性能，适合无需实时获取最新数据的场景。
 *
 *@author: Locyk
 *@time: 2025/11/14
 *
 */
public class CopyOnWriteWeakConsistency {
    public static void main(String[] args) throws InterruptedException{
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("A");
        list.add("B");

        // 线程1：遍历集合（迭代器创建时的快照是 [A, B]）
        new Thread(() -> {
            Iterator<String> iterator = list.iterator();
            System.out.print("迭代器遍历结果：");
            while (iterator.hasNext()) {
                System.out.print(iterator.next() + " ");
                try {
                    Thread.sleep(1000); // 遍历期间暂停1秒，给其他线程修改时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 输出：A B（看不到线程2添加的C）
        }).start();

        // 线程2：在遍历期间添加元素
        Thread.sleep(500); // 等待线程1创建迭代器
        list.add("C");
        System.out.println("\n集合实际大小：" + list.size()); // 输出：3
    }
}
