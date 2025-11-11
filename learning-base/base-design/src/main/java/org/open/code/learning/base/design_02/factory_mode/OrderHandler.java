package org.open.code.learning.base.design_02.factory_mode;

/**
 * 订单处理器接口
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public interface OrderHandler {
    void process(Order order);

    String getOrderType();
}
