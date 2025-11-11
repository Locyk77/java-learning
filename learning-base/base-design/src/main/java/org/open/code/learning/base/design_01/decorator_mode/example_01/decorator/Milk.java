package org.open.code.learning.base.design_01.decorator_mode.example_01.decorator;

import org.open.code.learning.base.design_01.decorator_mode.example_01.Coffee;
import org.open.code.learning.base.design_01.decorator_mode.example_01.CondimentDecorator;

/**
 * 具体装饰器:牛奶
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class Milk extends CondimentDecorator {

    public Milk(Coffee coffee) {
        super(coffee);
    }

    @Override
    public Integer getPrice() {
        Integer price = super.getPrice();
        return price + 5;
    }

    @Override
    public String getDescription() {
        String description = super.getDescription();
        return description + " + 牛奶";
    }
}
