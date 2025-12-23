package org.open.code.learning.collection;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * 基于单向链表实现的自定义顺序集合
 *
 *@author: Locyk
 *@time: 2025/11/18
 *
 */
public class SinglyLinkedList<T> {

    private static class Node<T> {
        T data;         //节点数据
        Node<T> next;   //后继节点

        Node(T data) {
            this(data, null);
        }

        Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node<T> head;  //头结点
    private int size;      //元素个数

    public SinglyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * 尾部添加元素
     * @param element
     */
    public void add(T element) {
        this.add(size, element);
    }

    /**
     * 指定索引插入元素
     * @param index    插入位置（0 <= index <= size）
     * @param element  待插入元素
     */
    public void add(int index, T element) {
        Node<T> newNode = new Node<>(element);
        if (head == null) {//空链表插入
            head = newNode;
        } else {
            if (index == 0) {//头部插入
                newNode.next = head;
                head = newNode;
            } else if (index == size) {//尾部插入
                Node<T> prev = getNode(index - 1);
                prev.next = newNode;
            } else {//中部插入
                Node<T> prev = getNode(index - 1);
                Node<T> target = prev.next;
                newNode.next = target;
                prev.next = newNode;
            }
        }
        size++;
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
     * 判断集合是否为空
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 返回元素个数
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 获取指定索引的节点（内部辅助方法）
     *
     * @param index
     * @return
     */
    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();
        list.add("A");
        list.add("B");
        System.out.println(list.get(0) + "," + list.get(1) + ":" + list.size());
        list.add(0, "C");
        System.out.println(list.get(0) + "," + list.get(1) + ":" + list.size());
        list.add(1, "D");
        System.out.println(list.get(0) + "," + list.get(1) + ":" + list.size());
    }

}
