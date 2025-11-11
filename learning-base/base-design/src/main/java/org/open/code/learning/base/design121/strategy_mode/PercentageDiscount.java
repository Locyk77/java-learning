package org.open.code.learning.base.design121.strategy_mode;

/**
 * 百分比折扣策略
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class PercentageDiscount implements DiscountStrategy {
    private double percentage; // 折扣百分比

    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(double originalPrice) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("折扣百分比必须在0-100之间");
        }
        return originalPrice * (1 - percentage / 100);
    }
}
