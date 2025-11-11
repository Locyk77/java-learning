package org.open.code.learning.base.design_01.strategy_mode;

/**
 * 1-2-1、深入理解常用设计模式
 * 策略模式
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class DesignMain {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        double originalPrice = 100.0; // 原价100元

        // 使用无折扣策略
        cart.setDiscountStrategy(new NoDiscount());
        System.out.println("无折扣价格: " + cart.calculateFinalPrice(originalPrice));

        // 使用20%折扣策略
        cart.setDiscountStrategy(new PercentageDiscount(20));
        System.out.println("20%折扣后价格: " + cart.calculateFinalPrice(originalPrice));

        // 使用30元固定折扣策略
        cart.setDiscountStrategy(new FixedAmountDiscount(30));
        System.out.println("30元固定折扣后价格: " + cart.calculateFinalPrice(originalPrice));

        // 动态切换为10%折扣策略
        cart.setDiscountStrategy(new PercentageDiscount(10));
        System.out.println("10%折扣后价格: " + cart.calculateFinalPrice(originalPrice));
    }
}
