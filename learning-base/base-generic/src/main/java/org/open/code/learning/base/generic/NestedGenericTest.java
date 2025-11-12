package org.open.code.learning.base.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *@author: Locyk
 *@time: 2025/11/12
 *
 */
public class NestedGenericTest {

    // 泛型方法：计算指定用户的所有订单总金额
    public static double calculateTotalAmount(Map<String, Map<String, List<Order>>> orderMap, String userId) {
        if (orderMap == null || userId == null) {
            return 0.0;
        }
        // 1. 获取用户的所有订单类型映射
        Map<String, List<Order>> userOrderMap = orderMap.get(userId);
        if (userOrderMap == null) {
            return 0.0;
        }
        // 2. 遍历所有订单类型，累加金额
        double total = 0.0;
        for (List<Order> orderList : userOrderMap.values()) {
            for (Order order : orderList) {
                total += order.getAmount();
            }
        }
        return total;
    }

    public static void main(String[] args) {
        // 1. 初始化嵌套泛型Map
        Map<String, Map<String, List<Order>>> orderMap = new HashMap<>();

        // 2. 添加用户u1001的订单
        Map<String, List<Order>> u1001Orders = new HashMap<>();
        List<Order> takeawayOrders = new ArrayList<>();
        takeawayOrders.add(new Order("o1", 30.5));
        takeawayOrders.add(new Order("o2", 50.0));
        u1001Orders.put("外卖订单", takeawayOrders);

        List<Order> freshOrders = new ArrayList<>();
        freshOrders.add(new Order("o3", 88.9));
        u1001Orders.put("生鲜订单", freshOrders);
        orderMap.put("u1001", u1001Orders);

        // 3. 添加用户u1002的订单
        Map<String, List<Order>> u1002Orders = new HashMap<>();
        List<Order> takeawayOrders2 = new ArrayList<>();
        takeawayOrders2.add(new Order("o4", 25.8));
        u1002Orders.put("外卖订单", takeawayOrders2);
        orderMap.put("u1002", u1002Orders);

        // 4. 计算u1001的总金额
        double u1001Total = calculateTotalAmount(orderMap, "u1001");
        System.out.println("u1001的总订单金额：" + u1001Total); // 30.5+50.0+88.9=169.4
    }
}

// 自定义Order类
class Order {
    private String orderId;
    private double amount;

    public Order(String orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    // getter
    public double getAmount() {
        return amount;
    }
}