package org.open.code.learning.base.design_01.decorator_mode.example_01.specific;

import org.open.code.learning.base.design_01.decorator_mode.example_01.Coffee;

/**
 * 具体组件：拿铁
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class Latte implements Coffee {
    @Override
    public Integer getPrice() {
        return 40;
    }

    @Override
    public String getDescription() {
        return "拿铁";
    }
}
