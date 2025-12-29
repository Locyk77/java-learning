package org.open.code.learning.concurrency.fork;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * 拆分任务进行文本中字符串的替换
 *
 *@author: Locyk
 *@time: 2025/12/25
 *
 */
public class FileContentReplaceTask extends RecursiveAction {

    private static final int FILE_THRESHOLD = 5; // 阈值：单个任务处理的文件数少于5个直接处理
    private final List<File> txtFiles; // 当前任务要处理的.txt文件列表（非目录）
    private final String targetStr; // 目标字符串（要替换的）
    private final String replaceStr; // 替换字符串

    // 构造方法1：初始化根目录（对外暴露）
    public FileContentReplaceTask(File rootDir, String targetStr, String replaceStr) {
        this(scanAllTxtFiles(rootDir), targetStr, replaceStr);
    }

    // 构造方法2：初始化文件列表（内部使用，拆分任务用）
    private FileContentReplaceTask(List<File> txtFiles, String targetStr, String replaceStr) {
        this.txtFiles = txtFiles;
        this.targetStr = targetStr;
        this.replaceStr = replaceStr;
    }

    // 工具方法：递归扫描目录下所有.txt文件（返回文件列表，不含目录）
    private static List<File> scanAllTxtFiles(File dir) {
        List<File> allTxtFiles = new ArrayList<>();
        if (!dir.exists() || !dir.isDirectory()) {
            return allTxtFiles;
        }

        File[] files = dir.listFiles();
        if (files == null) {
            return allTxtFiles;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                // 递归扫描子目录
                allTxtFiles.addAll(scanAllTxtFiles(file));
            } else if (file.getName().endsWith(".txt")) {
                // 收集.txt文件
                allTxtFiles.add(file);
            }
        }
        return allTxtFiles;
    }

    @Override
    protected void compute() {
        // 1. 递归终止条件：文件数小于阈值，直接处理
        if (txtFiles.size() <= FILE_THRESHOLD) {
            long processedCount = 0;
            for (File file : txtFiles) {
                replaceInFile(file);
                processedCount++;
            }
            System.out.println("当前任务处理完成 " + processedCount + " 个文件（阈值内直接处理）");
            return;
        }

        // 2. 任务过大，拆分为两个子任务（均分文件列表）
        int mid = txtFiles.size() / 2;
        List<File> leftFiles = txtFiles.subList(0, mid);
        List<File> rightFiles = txtFiles.subList(mid, txtFiles.size());

        FileContentReplaceTask leftTask = new FileContentReplaceTask(leftFiles, targetStr, replaceStr);
        FileContentReplaceTask rightTask = new FileContentReplaceTask(rightFiles, targetStr, replaceStr);

        // 3. 执行子任务（invokeAll会等待所有子任务完成，无需手动fork/join）
        invokeAll(leftTask, rightTask);
    }
    // 单个文件的字符串替换逻辑（增加异常详细信息，避免覆盖空文件）
    private void replaceInFile(File file) {
        try {
            // 读取文件所有行（UTF-8编码）
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            // 替换目标字符串（创建新列表，避免修改原列表）
            List<String> newLines = new ArrayList<>();
            boolean hasChange = false;
            for (String line : lines) {
                String newLine = line.replace(targetStr, replaceStr);
                newLines.add(newLine);
                if (!newLine.equals(line)) {
                    hasChange = true;
                }
            }

            // 仅当内容有变化时才写回文件（减少IO操作）
            if (hasChange) {
                Files.write(file.toPath(), newLines, StandardCharsets.UTF_8);
                System.out.println("已替换文件：" + file.getAbsolutePath());
            } else {
                System.out.println("文件无匹配内容：" + file.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("处理文件失败：" + file.getAbsolutePath() + "，错误信息：" + e.getMessage());
        }
    }
}
