package org.open.code.learning.base.design_01.strategy_mode;

/**
 * 折扣策略接口
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public interface DiscountStrategy {

    // 计算折扣后价格
    double calculateDiscount(double originalPrice);
}
