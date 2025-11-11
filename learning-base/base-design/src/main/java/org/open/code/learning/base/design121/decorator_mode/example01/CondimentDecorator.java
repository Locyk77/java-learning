package org.open.code.learning.base.design121.decorator_mode.example01;

/**
 * 抽象装饰器:配料
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public abstract class CondimentDecorator implements Coffee {
    protected Coffee coffee;

    public CondimentDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public Integer getPrice() {
        return coffee.getPrice();
    }

    @Override
    public String getDescription() {
        return coffee.getDescription();
    }
}
