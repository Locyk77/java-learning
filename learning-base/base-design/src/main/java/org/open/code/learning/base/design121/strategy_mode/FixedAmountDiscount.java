package org.open.code.learning.base.design121.strategy_mode;

/**
 * 固定金额折扣策略
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class FixedAmountDiscount implements DiscountStrategy {
    private double discountAmount; // 折扣金额

    public FixedAmountDiscount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public double calculateDiscount(double originalPrice) {
        if (discountAmount < 0) {
            throw new IllegalArgumentException("折扣金额不能为负数");
        }
        return Math.max(originalPrice - discountAmount, 0); // 确保折扣后价格不小于0
    }
}