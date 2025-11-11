package org.open.code.learning.base.design121.decorator_mode.example01.decorator;

import org.open.code.learning.base.design121.decorator_mode.example01.Coffee;
import org.open.code.learning.base.design121.decorator_mode.example01.CondimentDecorator;

/**
 * 具体装饰器：糖
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class Sugar extends CondimentDecorator {
    public Sugar(Coffee coffee) {
        super(coffee);
    }

    @Override
    public Integer getPrice() {
        Integer price = super.getPrice();
        return price + 3;
    }

    @Override
    public String getDescription() {
        String description = super.getDescription();
        return description + " + 糖";
    }
}
