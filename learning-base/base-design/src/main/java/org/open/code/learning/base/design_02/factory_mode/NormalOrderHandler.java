package org.open.code.learning.base.design_02.factory_mode;

import org.springframework.stereotype.Component;

/**
 * 普通订单处理器
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
@Component
public class NormalOrderHandler implements OrderHandler {
    @Override
    public void process(Order order) {
        System.out.println("处理普通订单：" + order.getId());
        // 普通订单处理逻辑...
    }

    @Override
    public String getOrderType() {
        return "NORMAL";
    }
}