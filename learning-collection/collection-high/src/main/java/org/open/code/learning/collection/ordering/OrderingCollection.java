package org.open.code.learning.collection.ordering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 *
 *@author: Locyk
 *@time: 2025/11/14
 *
 */
public class OrderingCollection {

    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("10", "1");
        map.put("b", "3");
        map.put("30", "2");
        map.put("20", "2");
        for(int i=0;i<10;i++){
            System.out.println(map);
        }

        System.out.println("=================");


        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("10", "1");
        hashMap.put("b", "3");
        hashMap.put("30", "2");
        hashMap.put("20", "2");
        for(int i=0;i<10;i++){
            System.out.println(hashMap);
        }

        ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);
        System.out.println("排序前 - 存储/遍历顺序：" + list); // 输出 [3, 1, 2]

        // 2. 执行排序（修改集合内部存储）
        Collections.sort(list); // 或 list.sort(null)（JDK 8+ 新增）

        // 3. 排序后 - 存储/遍历顺序变为排序后的顺序：1 → 2 → 3
        System.out.println("排序后 - 存储/遍历顺序：" + list); // 输出 [1, 2, 3]

        // 4. 新增元素：按“当前存储顺序的尾部”插入（维持排序后的顺序）
        list.add(0);
        System.out.println("新增元素后 - 存储/遍历顺序：" + list); // 输出 [1, 2, 3, 0]
    }
}
