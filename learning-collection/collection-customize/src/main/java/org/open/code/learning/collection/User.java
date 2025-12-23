package org.open.code.learning.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 *@author: Locyk
 *@time: 2025/11/17
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String name;
    private int loginCount;


    public static void main(String[] args) {
        List<User> list = new ArrayList<>();

        // 1. 创建对象，user1 引用指向对象A（地址0x111）
        User user1 = new User("Locyk", 1);
        User user2 = new User("CuiC", 2);

        // 2. 把 user1、user2 的引用（地址）存入集合
        list.add(user1); // 集合第0位存 0x111（指向对象A）
        list.add(user2); // 集合第1位存 0x222（指向对象B）
        System.out.println("=== 初始状态（集合存的是对象地址）===");
        System.out.println("集合中的user1：" + list.get(0)); // 打印对象A：name=Locyk, loginCount=1
        System.out.println("直接访问user1：" + user1);       // 同样打印对象A（和集合指向同一个）

        // ------------------------------
        // 场景1：修改「对象内部属性」（验证：所有持有该对象地址的引用都会受影响）
        // ------------------------------
        user1.setLoginCount(3); // 拿着 0x111 地址，修改对象A的属性
        System.out.println("\n=== 场景1：修改对象内部属性后 ===");
        System.out.println("集合中的user1：" + list.get(0)); // 受影响！打印 loginCount=3（还是指向0x111）
        System.out.println("直接访问user1：" + user1);       // 受影响！打印 loginCount=3（还是指向0x111）

        // ------------------------------
        // 场景2：修改「引用变量的指向」（验证：不会影响之前的地址赋值）
        // ------------------------------
        // 创建新对象C（地址0x333），把 user1 的引用指向从0x111改成0x333
        user1 = new User("NewLocyk", 100);
        System.out.println("\n=== 场景2：修改user1的引用指向后 ===");
        System.out.println("集合中的user1：" + list.get(0)); // 不受影响！仍然打印对象A（loginCount=3，地址0x111）
        System.out.println("直接访问user1：" + user1);       // 指向新对象C（loginCount=100，地址0x333）


        user1.setLoginCount(4);
        System.out.println("\n=== 场景3：修改user1的属性后 ===");
        System.out.println("集合中的user1：" + list.get(0)); // 不受影响！仍然打印对象A（loginCount=3，地址0x111）
        System.out.println("直接访问user1：" + user1);       // 指向新对象C（loginCount=4，地址0x333）

    }
}
