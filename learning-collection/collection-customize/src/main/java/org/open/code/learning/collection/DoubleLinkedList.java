package org.open.code.learning.collection;

import java.util.NoSuchElementException;

/**
 * 基于双向链表实现的自定义顺序集合
 *
 *@author: Locyk
 *@time: 2025/11/17
 *
 */
public class DoubleLinkedList<T> {
    //链表节点内部类
    private static class Node<T> {
        T data;          //节点数据
        Node<T> prev;    //前驱节点
        Node<T> next;    //后继节点

        Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<T> head; //头结点
    private Node<T> tail; //尾节点
    private int size;     //元素个数

    public DoubleLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * 尾部添加元素
     * @param element
     */
    public void add(T element) {
        this.add(this.size, element);
    }

    /**
     * 指定索引插入元素
     * @param index 插入位置（0 <= index <= size）
     * @param element 待插入元素
     */
    public void add(int index, T element) {
        //校验索引合法性
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        //尾部插入（包含空链表）
        if (index == size) {
            Node<T> newNode = new Node<>(element, tail, null);
            //空链表，头结点和尾节点指向新节点
            if (tail == null) {
                head = newNode;
            } else {//非空链表，尾节点的next指向新节点
                tail.next = newNode;
            }
            tail = newNode;//更新尾节点
        } else {//中间插入或头部插入
            Node<T> target = getNode(index);//获取目标位置节点
            Node<T> prevNode = target.prev;
            Node<T> newNode = new Node<>(element, prevNode, target);
            if (prevNode == null) {//头部插入（index=0）
                head = newNode;
            } else {
                prevNode.next = newNode;
            }
            target.prev = newNode;
        }
        size++;
    }

    /**
     * 删除指定索引元素
     * @param index 待删除元素索引
     */
    public void remove(int index) {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> target = getNode(index);
        Node<T> preNode = target.prev;
        Node<T> nextNode = target.next;

        //处理前驱节点
        if (preNode == null) {//删除头结点
            head = nextNode;
        } else {
            preNode.next = nextNode;
        }

        //处理后继结点
        if (nextNode == null) {//删除尾节点
            tail = preNode;
        } else {
            nextNode.prev = preNode;
        }

        //帮助GC
        target.data = null;
        target.prev = null;
        target.next = null;

        size--;

    }

    /**
     * 获取指定索引的元素
     * @param index 元素索引
     * @return
     */
    public T get(int index) {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return getNode(index).data;
    }

    /**
     * 返回元素个数
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 判断集合是否为空
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 获取指定索引的节点（内部辅助方法）
     * @param index
     * @return
     */
    private Node<T> getNode(int index) {
        //优化：根据索引位置选择从头或从尾遍历
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }


    public static void main(String[] args) {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();
        // 测试尾部添加
        list.add("A");
        list.add("B");
        list.add("C");
        System.out.println("尾部添加后（size=" + list.size() + "）：" + list.get(0) + "," + list.get(1) + "," + list.get(2)); // A,B,C

        // 测试指定索引插入
        list.add(1, "D");
        System.out.println("插入索引1后（size=" + list.size() + "）：" + list.get(0) + "," + list.get(1) + "," + list.get(2)); // A,D,B

        // 测试删除
        list.remove(2);
        System.out.println("删除索引2后（size=" + list.size() + "）：" + list.get(0) + "," + list.get(1) + "," + list.get(2)); // A,D,C


        // 测试边界条件
        try {
            list.add(5, "E"); // 索引越界
        } catch (IndexOutOfBoundsException e) {
            System.out.println("预期异常：" + e.getMessage());
        }

        try {
            list.get(3); // 索引越界
        } catch (IndexOutOfBoundsException e) {
            System.out.println("预期异常：" + e.getMessage());
        }
    }


}
