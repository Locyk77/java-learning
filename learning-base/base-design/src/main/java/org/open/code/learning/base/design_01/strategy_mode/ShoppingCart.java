package org.open.code.learning.base.design_01.strategy_mode;

/**
 * 购物车类（上下文类）
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class ShoppingCart {
    private DiscountStrategy discountStrategy; // 当前使用的折扣策略

    // 设置折扣策略
    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    // 计算最终价格
    public double calculateFinalPrice(double originalPrice) {
        if (discountStrategy == null) {
            discountStrategy = new NoDiscount(); // 默认无折扣
        }
        return discountStrategy.calculateDiscount(originalPrice);
    }
}
