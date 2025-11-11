package org.open.code.learning.base.design121.decorator_mode.example01.specific;

import org.open.code.learning.base.design121.decorator_mode.example01.Coffee;

/**
 * 具体组件：美式咖啡
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class Americano implements Coffee {

    @Override
    public Integer getPrice() {
        return 30;
    }

    @Override
    public String getDescription() {
        return "美式咖啡";
    }
}
