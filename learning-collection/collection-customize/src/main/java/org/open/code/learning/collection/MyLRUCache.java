package org.open.code.learning.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU缓存集合
 * 基于双向链表+哈希表实现的LRU缓存（最近使用优先保留）
 *@author: Locyk
 *@time: 2025/11/19
 *
 */
public class MyLRUCache<K, V> {

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;              //缓存最大容量
    private final Map<K, Node<K, V>> map;    //哈希表（O(1)查询节点）
    private Node<K, V> head;                 //头结点（最近使用的元素）
    private Node<K, V> tail;                 //尾节点（最少使用的元素）
    private int size;                        //当前元素个数

    /**
     * 构造指定容量的LRU缓存
     * @param capacity 缓存容量（必须>0）
     */
    public MyLRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive: " + capacity);
        }
        this.capacity = capacity;
        this.map = new HashMap<>();
        //初始化哨兵节点（简化边界处理）
        this.head = new Node<>(null, null);
        this.tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
        this.size = 0;
    }

    /**
     * 获取键对应的值（存在则移至队首，不存在返回null）
     * @param key
     * @return
     */
    public V get(K key) {
        Node<K, V> node = map.get(key);
        if (node == null) {
            return null;
        }
        //移至队首（标记为最近使用）
        moveToHead(node);
        return node.value;
    }

    public void put(K key, V value) {
        Node<K, V> node = map.get(key);
        if (node != null) {
            //键已存在，更新值并移至队首
            node.value = value;
            moveToHead(node);
            return;
        }
        //键不存在，创建新节点
        Node<K, V> newNode = new Node<>(key, value);
        map.put(key, newNode);
        addToHead(newNode);//新节点移至队首
        size++;
        //容量满，删除队尾最少使用元素
        if (size > capacity) {
            Node<K, V> removedNode = removeTail();
            map.remove(removedNode.key);//哈希表中移除
            size--;
        }
    }

    /**
     * 返回当前元素个数
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 返回缓存容量
     * @return
     */
    public int getCapacity() {
        return capacity;
    }


    /**
     * 将节点移至队首（先删除再添加到头部）
     * @param node
     */
    private void moveToHead(Node<K, V> node) {
        removeNode(node);
        addToHead(node);
    }

    /**
     * 将节点添加到队首（哨兵节点之后）
     * @param node
     */
    private void addToHead(Node<K, V> node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    /**
     * 从链表中删除节点
     * @param node
     */
    private void removeNode(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    /**
     * 删除队尾节点（哨兵节点之前）
     * @return
     */
    private Node<K, V> removeTail() {
        Node<K, V> node = tail.prev;
        removeNode(node);
        return node;
    }

    public static void main(String[] args) {
        MyLRUCache<Integer, String> lru = new MyLRUCache<>(3); // 容量3
        // 测试添加3个元素
        lru.put(1, "A");
        lru.put(2, "B");
        lru.put(3, "C");
        System.out.println("添加3个元素后，size=" + lru.size() + "：" + lru.get(1) + "," + lru.get(2) + "," + lru.get(3)); // A,B,C

        // 测试访问元素后移至队首
        lru.get(2); // B移至队首
        System.out.println(lru.get(0));
        lru.put(4, "D"); // 容量满，删除队尾最少使用的1（A）
        System.out.println(lru.get(0));
        System.out.println("添加4后，size=" + lru.size() + "：" + lru.get(1) + "," + lru.get(2) + "," + lru.get(3) + "," + lru.get(4)); // null,B,C,D

        // 测试更新已存在的键
        lru.put(3, "C-Updated");
        System.out.println("更新3后，get(3)：" + lru.get(3)); // C-Updated

        // 测试容量为0的异常
        try {
            new MyLRUCache<>(0);
        } catch (IllegalArgumentException e) {
            System.out.println("预期异常：" + e.getMessage());
        }
    }
}
