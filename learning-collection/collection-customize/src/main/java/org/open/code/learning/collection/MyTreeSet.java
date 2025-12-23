package org.open.code.learning.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 自定义有序不可重复集合
 *
 *@author: Locyk
 *@time: 2025/11/20
 *
 */
public class MyTreeSet<T extends Comparable<T>> implements Iterable<T> {


    private static class Node<T> {
        T data;
        Node<T> left;   // 左子树（小于当前节点）
        Node<T> right;  // 右子树（大于当前节点）

        Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node<T> root; //根节点
    private int size;    //元素个数

    public MyTreeSet() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new InorderIterator();
    }

    private class InorderIterator implements Iterator<T> {
        private Node<T> current = root;
        private Node<T> prev = null;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * 以中序遍历为例，核心步骤是：
         * 对当前节点，找到其中序前驱节点（左子树的最右侧节点）。
         * 若前驱节点的右指针为空：将其指向当前节点（建立回溯链接），然后遍历左子树。
         * 若前驱节点的右指针已指向当前节点：说明左子树已遍历完成，恢复前驱节点的右指针（清空回溯链接），访问当前节点，然后遍历右子树。
         *
         *
         * 一。假设 加入的元素顺序为 5、3、4
         *
         * 第一步：进入大循环，prev= 3 ， prev在小循环中最终停在4，prev = 4
         * 第二步：prev.right = 5（4的临时右节点是5）,   current = 3
         * 第三步：进入大循环，打印3，current = 4（右节点是5）
         * 第四步：进入大循环，打印4，current = 5
         * 第五步：进入大循环，prev =3 ， prev在小循环中最终停在4，prev = 4
         * 第六步：prev.right = null（取消4的临时右节点），打印5，current = current.right（current此时为null）
         * 第七步：current为null，结束遍历
         *
         *
         * 二、假设如下图
         *    5
         *    / \
         *   3   7
         *  / \   \
         * 1   4   9
         *
         * 1、current=5，左子树存在，找到前驱节点4（3 的右子树最右侧），4.right=null → 设为5，current=3。
         * 2、current=3，左子树存在，找到前驱节点1（1 的右子树为空），1.right=null → 设为3，current=1。
         * 3、current=1，左子树为空 → 访问1，current=1.right=3。
         * 4、current=3，左子树存在，找到前驱节点1（1.right=3）→ 恢复1.right=null，访问3，current=3.right=4。
         * 5、current=4，左子树为空 → 访问4，current=4.right=5。
         * 6、current=5，左子树存在，找到前驱节点4（4.right=5）→ 恢复4.right=null，访问5，current=5.right=7。
         * 7、current=7，左子树为空 → 访问7，current=7.right=9。
         * 8、current=9，左子树为空 → 访问9，current=9.right=null，遍历结束。
         *
         * @return
         */
        @Override
        public T next() {
            T result = null;
            while (current != null) {
                if (current.left == null) {
                    //左子树为空，访问当前节点
                    result = current.data;
                    current = current.right;
                    break;
                } else {
                    //找到当前节点的前驱节点
                    prev = current.left;
                    while (prev.right != null && prev.right != current) {
                        prev = prev.right;
                    }
                    if (prev.right == null) {
                        //前驱节点右指针指向当前节点（标记）
                        prev.right = current;
                        current = current.left;
                    } else {
                        //前驱节点右指针已指向当前节点，访问当前节点
                        prev.right = null;//恢复树结构
                        result = current.data;
                        current = current.right;
                        break;
                    }
                }
            }
            if (result == null) {
                System.out.println("-----");
                throw new NoSuchElementException("没有下一个元素");
            }
            return result;
        }
    }

    public boolean add(T element) {
        if (element == null) {
            throw new NullPointerException("元素不能为null");
        }
        //树为空，创建根节点
        if (root == null) {
            root = new Node<>(element);
            size++;
            return true;
        }
        //遍历BST，找到插入位置
        Node<T> current = root;
        Node<T> parent = null;
        int cmp = 0;
        while (current != null) {
            parent = current;
            cmp = element.compareTo(current.data);
            if (cmp < 0) {//元素小于当前节点，走左子树
                current = current.left;
            } else if (cmp > 0) {//元素大于当前节点，走右子树
                current = current.right;
            } else {//元素相等，不插入（不可重复）
                return false;
            }
        }
        if (cmp < 0) {
            parent.left = new Node<>(element);
        } else {
            parent.right = new Node<>(element);
        }
        size++;
        return true;
    }

    public boolean remove(T element) {
        if (element == null || root == null) {
            return false;
        }
        //查找待删除节点及其父节点
        Node<T> current = root;
        Node<T> parent = null;
        int cmp = 0;
        boolean isLeftChild = false;
        //找到待删除节点
        while (current != null) {
            cmp = element.compareTo(current.data);
            if (cmp == 0) {
                break;
            }
            parent = current;
            if (cmp < 0) {
                current = current.left;
                isLeftChild = true;
            } else {
                current = current.right;
                isLeftChild = false;
            }
        }
        if (current == null) {
            return false;//元素不存在
        }
        // 情况1：待删除节点为叶子节点（无左右子树）
        if (current.left == null && current.right == null) {
            if (parent == null) {//根节点是叶子节点
                root = null;
            } else if (isLeftChild) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
        //情况2：待删除节点只有右子树
        else if (current.left == null) {
            if (parent == null) {
                root = current.right;
            } else if (isLeftChild) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        }
        //情况3：待删除节点只有左子树
        else if (current.right == null) {
            if (parent == null) {
                root = current.left;
            } else if (isLeftChild) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
        }
        //情况4：待删除节点有左右子树（找中序后继节点替换）
        else {
            Node<T> successorParent = current;
            Node<T> successor = current.right;

            //找到右子树的最左节点（中序后继）
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }

            //替换待删除节点的数据
            current.data = successor.data;

            //删除后继节点
            if (successorParent == current) {
                successorParent.right = successor.right;
            } else {
                successorParent.left = successor.right;
            }
        }
        size--;
        return true;
    }

    public boolean contains(T element) {
        if (element == null || root == null) {
            return false;
        }
        Node<T> current = root;
        while (current != null) {
            int cmp = element.compareTo(current.data);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return true; //找到元素
            }
        }
        return false;
    }

    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException("集合为空");
        }
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.data;
    }

    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException("集合为空");
        }
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * 遍历二叉搜索树
     * @param node
     * @param type
     */
    public void traverse(Node<T> node, int type) {
        if (node == null) {
            return;
        }
        switch (type) {
            case 1:
                //从左遍历到右
                System.out.println(node.data + " ");
                traverse(node.left, type);
                traverse(node.right, type);
                break;
            case 2:
                //从右遍历到左
                System.out.println(node.data + " ");
                traverse(node.right, type);
                traverse(node.left, type);
                break;
            case 3:
                //升序
                traverse(node.left, type);
                System.out.println(node.data + " ");
                traverse(node.right, type);
                break;
            case 4:
                //后序遍历（一般用于删除场景，按顺序先删除子节点，在删除父节点）
                traverse(node.left, type);
                traverse(node.right, type);
                System.out.println(node.data + " ");
                break;
            case 5:
                //后序遍历（不推荐）
                traverse(node.right, type);
                traverse(node.left, type);
                System.out.println(node.data + " ");
                break;
            case 6:
                //降序
                traverse(node.right, type);
                System.out.println(node.data + " ");
                traverse(node.left, type);
                break;
        }
    }


    /**
     * 0、获取最多的层数/获取最长路径 ✅
     * 1、打印指定层级所有数据  ❌
     * 2、打印二叉树结构     ❌
     * 3、共有多少条路径    ✅
     * 4、打印所有路径     ✅
     * 5、打印前三条路径   ✅
     * 6、打印后三条路径   ✅
     * 7、最后节点有多少个  ✅
     * 8、最后节点属于左节点的个数  ✅
     * 9、最后节点属于右节点的个数  ✅
     */


    /**
     * 打印二叉树结构
     * @param node
     */
    public void printTree(Node<T> node) {

    }

    /**
     * 获取所有路径
     * @return
     */
    public List<List<T>> getAllPath() {
        List<T> currentPath = new ArrayList<>();
        List<List<T>> result = new ArrayList<>();
        dfs(root, currentPath, result);
        return result;
    }

    /**
     * 获取最大路径
     * @return
     */
    public List<T> getMaxPath() {
        List<T> currentPath = new ArrayList<>();
        List<T> result = new ArrayList<>();
        dfs(result, root, currentPath);
        return result;
    }

    /**
     * 获取升序路径前几条（前多少条）
     * @param total
     * @return
     */
    public List<List<T>> getAscPath(int total) {
        List<T> currentPath = new ArrayList<>();
        List<List<T>> result = new ArrayList<>();
        dfs(root, currentPath, result, total, true);
        return result;
    }

    /**
     * 获取降序路径前几条（后多少条）
     * @param total
     * @return
     */
    public List<List<T>> getDescPath(int total) {
        List<T> currentPath = new ArrayList<>();
        List<List<T>> result = new ArrayList<>();
        dfs(root, currentPath, result, total, false);
        return result;
    }

    /**
     * 获取最后节点属于左节点的个数
     * @return
     */
    public int lastLeftSize() {
        List<List<T>> list = getAllPath();
        if (list == null || list.size() == 0 || (list.size() == 1 && list.get(0).size() == 1)) {
            return 0;
        }
        int count = 0;
        for (List<T> path : list) {
            if (path.get(path.size() - 1).compareTo(path.get(path.size() - 2)) < 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取最后节点属于右节点的个数
     * @return
     */
    public int lastRightSize() {
        List<List<T>> list = getAllPath();
        if (list == null || list.size() == 0 || (list.size() == 1 && list.get(0).size() == 1)) {
            return 0;
        }
        int count = 0;
        for (List<T> path : list) {
            if (path.get(path.size() - 1).compareTo(path.get(path.size() - 2)) > 0) {
                count++;
            }
        }
        return count;
    }


    /**
     * 获取路径数量（最后节点的个数）
     * @return
     */
    public int pathSize() {
        return getAllPath().size();
    }


    /**
     * 全部路径获取--核心方法
     * @param node
     * @param currentPath
     * @param result
     */
    private void dfs(Node<T> node, List<T> currentPath, List<List<T>> result) {
        if (node == null) {
            return;
        }
        currentPath.add(node.data);
        if (node.left == null && node.right == null) {
            result.add(new ArrayList<>(currentPath));
        } else {
            dfs(node.left, currentPath, result);
            dfs(node.right, currentPath, result);
        }
        currentPath.remove(currentPath.size() - 1);
    }


    /**
     * 获取前几条路径/后几条路径
     * @param node
     * @param currentPath
     * @param result
     * @param total
     * @param type
     */
    private void dfs(Node<T> node, List<T> currentPath, List<List<T>> result, int total, boolean type) {
        if (node == null || result.size() >= total) {
            return;
        }
        currentPath.add(node.data);
        if (node.left == null && node.right == null) {
            result.add(new ArrayList<>(currentPath));
        } else {
            if (type) {
                dfs(node.left, currentPath, result, total, true);
                dfs(node.right, currentPath, result, total, true);
            } else {
                dfs(node.right, currentPath, result, total, false);
                dfs(node.left, currentPath, result, total, false);
            }
        }
        currentPath.remove(currentPath.size() - 1);
    }


    /**
     * 获取最长路径--核心方法
     * @param result
     * @param node
     * @param currentPath
     */
    private void dfs(List<T> result, Node<T> node, List<T> currentPath) {
        if (node == null) {
            return;
        }
        currentPath.add(node.data);
        if (node.left == null && node.right == null) {
            if (currentPath.size() > result.size()) {
                result.clear();
                result.addAll(currentPath);
            }
        } else {
            dfs(result, node.left, currentPath);
            dfs(result, node.right, currentPath);
        }
        currentPath.remove(currentPath.size() - 1);
    }

    /**
     * 示例打印二叉树结构
     * @param level
     */
    private void printTree(int level) {
        if (level == 0) {
            return;
        }
        if (level == 1) {
            System.out.println("AAA");
            return;
        }
        int sum = (int) Math.pow(2, level - 1);//底层总元素个数
        int length = 2 * sum + 1; //总长度
        for (int i = 1; i <= level; i++) {
            if (i == 1) {//首层
                int start = (int) Math.pow(2, level - i) + 1;//开始打印点
                for (int j = 1; j <= length; j++) {
                    if (j == start) {
                        System.out.print("AAA");
                    } else {
                        System.out.print("   ");
                    }
                }
            } else if (i < level) {//中间层
                System.out.println(" ");
                System.out.println(" ");
                int start = (int) Math.pow(2, level - i) + 1;//开始打印点
                int start_tmp = start;
                int flag = 0;
                int total = (int) Math.pow(2, i - 1); // 当前层元素个数
                int gap = (length - 2 * (start - 1) - total) / (total - 1); //间隔数

                for (int j = 1; j <= length; j++) {
                    if (j == start_tmp) {
                        if (flag == 0) {
                            System.out.print("BBB");
                        } else {
                            System.out.print("CCC");
                        }
                        start_tmp = start_tmp + gap + 1;
                        flag ^= 1;//异或切换
                    } else {
                        System.out.print("   ");
                    }
                }
            } else {//尾层
                System.out.println(" ");
                System.out.println(" ");
                int start_tmp = 2;
                int flag = 0;

                for (int j = 1; j <= length; j++) {
                    if (j == start_tmp) {
                        if (flag == 0) {
                            System.out.print("BBB");
                        } else {
                            System.out.print("CCC");
                        }
                        start_tmp = start_tmp + 2;
                        flag ^= 1;//异或切换
                    } else {
                        System.out.print("   ");
                    }
                }
            }
        }
        System.out.println(" ");
    }


    public static void main(String[] args) {
        MyTreeSet<Integer> treeSet = new MyTreeSet<>();

        // 测试添加（无序插入，迭代器升序输出）
        treeSet.add(5);
        treeSet.add(3);
        treeSet.add(7);
        treeSet.add(3); // 重复元素，添加失败
        treeSet.add(9);
        treeSet.add(1);
        treeSet.add(4);
        treeSet.add(6);
        treeSet.add(15);

        System.out.print("升序遍历：");
        for (int num : treeSet) {
            System.out.print(num + " "); // 1 3 5 7 9
        }
        System.out.println();

        // 测试first和last
        System.out.println("最小元素：" + treeSet.first()); // 1
        System.out.println("最大元素：" + treeSet.last()); // 9

        // 测试contains
        System.out.println("是否包含7：" + treeSet.contains(7)); // true
        System.out.println("是否包含4：" + treeSet.contains(4)); // false

        // 测试删除（删除根节点5）
        treeSet.remove(5);
        System.out.print("删除5后遍历：");
        for (int num : treeSet) {
            System.out.print(num + " "); // 1 3 7 9
        }
        System.out.println();

        // 测试删除叶子节点3
        treeSet.remove(3);
        System.out.print("删除3后遍历：");
        for (int num : treeSet) {
            System.out.print(num + " "); // 1 7 9
        }
        System.out.println();

    }
}
