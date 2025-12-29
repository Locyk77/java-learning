package org.open.code.learning.concurrency.io;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * 磁盘IO阻塞示例
 *@author: Locyk
 *@time: 2025/12/4
 *
 */
public class DiskIOBlockExample {
    public static void main(String[] args) {
        // 1. 定义大文件路径（建议准备一个100MB以上的文件，放大阻塞效果）
        String filePath = "J:\\18.tar.gz";

        // 2. 打印主线程初始状态（RUNNABLE，但底层会阻塞）
        System.out.printf("主线程初始状态：%s%n", Thread.currentThread().getState());

        long startTime = System.currentTimeMillis();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] buffer = new byte[1024 * 1024]; // 1MB缓冲区
            int readLen;

            // 3. 核心阻塞点：read()方法会阻塞等待磁盘数据
            System.out.println("开始读取文件（磁盘IO阻塞开始）...");
            while ((readLen = fis.read(buffer)) != -1) {
                // 读取到数据后才会执行此处逻辑
                System.out.printf("读取到 %d 字节数据%n", readLen);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        // 4. 打印耗时（阻塞时间 = 磁盘读取时间）
        System.out.printf("文件读取完成，总耗时：%d 毫秒%n", endTime - startTime);
        System.out.printf("主线程最终状态：%s%n", Thread.currentThread().getState());
    }
}
