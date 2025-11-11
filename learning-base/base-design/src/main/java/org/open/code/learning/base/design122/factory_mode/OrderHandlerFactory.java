package org.open.code.learning.base.design122.factory_mode;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 订单处理器工厂
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
@Component
public class OrderHandlerFactory {
    private final Map<String, OrderHandler> handlerMap;

    // 注入所有订单处理器
    public OrderHandlerFactory(List<OrderHandler> handlers) {
        this.handlerMap = handlers.stream()
                .collect(Collectors.toMap(
                        OrderHandler::getOrderType,
                        Function.identity()
                ));
    }

    // 获取对应的处理器
    public OrderHandler getHandler(String orderType) {
        OrderHandler handler = handlerMap.get(orderType);
        if (handler == null) {
            throw new IllegalArgumentException("不支持的订单类型：" + orderType);
        }
        return handler;
    }
}