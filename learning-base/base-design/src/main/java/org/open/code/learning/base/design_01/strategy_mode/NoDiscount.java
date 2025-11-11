package org.open.code.learning.base.design_01.strategy_mode;

/**
 * 无折扣策略
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class NoDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double originalPrice) {
        return originalPrice; // 不打折，返回原价
    }
}
