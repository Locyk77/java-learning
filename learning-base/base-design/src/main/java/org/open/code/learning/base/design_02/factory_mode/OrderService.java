package org.open.code.learning.base.design_02.factory_mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单服务
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
@Service
public class OrderService {
    private final OrderHandlerFactory handlerFactory;

    @Autowired
    public OrderService(OrderHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    public void handleOrder(Order order) {
        // 通过工厂获取对应处理器
        OrderHandler handler = handlerFactory.getHandler(order.getType());
        handler.process(order);
    }
}
