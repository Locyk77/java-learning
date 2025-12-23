package org.open.code.learning.collection;

import java.util.*;

/**
 * 基于数组实现的动态集合（支持自动扩容、foreach遍历）
 *@author: Locyk
 *@time: 2025/11/18
 *
 */
public class MyArrayList<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 10;  //默认初始容量
    private static final float GROWTH_FACTOR = 1.5f; //扩容因子（1.5倍）
    private T[] elementData;                    //存储元素的数组
    private int size;                                //实际元素个数


    public MyArrayList() {
        this.elementData = (T[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * 尾部添加元素（满容量时自动扩容）
     * @param element
     */
    public void add(T element) {
        grow();
        elementData[size++] = element;
    }

    /**
     * 删除第一个匹配的元素
     * @param element 待删除元素（支持null）
     * @return
     */
    public boolean remove(T element) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(element, elementData[i])) {
                System.arraycopy(elementData, i + 1, elementData, i, size - i - 1);
                elementData[--size] = null; //帮助GC
                return true;
            }
        }
        return false;
    }

    /**
     * 判断集合是否包含指定元素
     * @param element
     * @return
     */
    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(element, elementData[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 清空集合
     */
    public void clear() {
        //遍历置为null，帮助GC
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    /**
     * TODO 存在一定问题
     * 转为数组返回（返回新数组，修改不影响原集合）
     * @return
     */
    public T[] toArray() {
        if (isEmpty()) {
            throw new IllegalStateException("Empty collection cannot return generic array");
        }
        // 从第一个元素获取类型，创建泛型数组
        T[] result = (T[]) java.lang.reflect.Array.newInstance(
                elementData[0].getClass(), size);
        System.arraycopy(elementData, 0, result, 0, size);
        return result;
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
     * 扩容逻辑：新容量
     */
    private void grow() {
        if (size == elementData.length) {
            int oldCapacity = elementData.length;
            int newCapacity = (int) Math.ceil(oldCapacity * GROWTH_FACTOR);
            T[] newArray = (T[]) new Object[newCapacity];
            System.arraycopy(elementData, 0, newArray, 0, oldCapacity);
            elementData = newArray;
            System.out.println("扩容：" + oldCapacity + " → " + newCapacity);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<T> {
        private int cursor = 0; // 下一个要返回的元素索引
        private boolean hasNextCalled = false; // 是否调用过hasNext()

        @Override
        public boolean hasNext() {
            hasNextCalled = true;
            return cursor < size;
        }

        @Override
        public T next() {
            if (!hasNextCalled && !hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            hasNextCalled = false;
            return elementData[cursor++];
        }
    }


    public static void main(String[] args) {

        MyArrayList<String> list = new MyArrayList<>();

        // 测试扩容（初始容量10，添加11个元素）
        for (int i = 0; i < 11; i++) {
            list.add("Element" + i);
        }
        System.out.println("扩容后size：" + list.size()); // 11

        // 测试contains
        System.out.println("是否包含Element5：" + list.contains("Element5")); // true
        System.out.println("是否包含null：" + list.contains(null)); // false

        // 测试remove
        list.add(null);
        System.out.println("删除null：" + list.remove(null)); // true
        System.out.println("删除不存在的元素：" + list.remove("NotFound")); // false

        // 测试foreach遍历
        System.out.print("foreach遍历：");
        for (String s : list) {
            System.out.print(s + " ");
        }
        System.out.println();

        // 测试toArray
        String[] array = list.toArray();
        System.out.println("数组长度：" + array.length); // 11

        // 测试clear
        list.clear();
        System.out.println("clear后size：" + list.size()); // 0
        System.out.println("clear后是否为空：" + list.isEmpty()); // true

        ArrayList arrayList = new ArrayList();
        arrayList.toArray();
        System.out.println();
    }
}
