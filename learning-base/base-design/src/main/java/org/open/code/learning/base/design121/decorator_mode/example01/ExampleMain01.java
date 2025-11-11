package org.open.code.learning.base.design121.decorator_mode.example01;

import org.open.code.learning.base.design121.decorator_mode.example01.decorator.Milk;
import org.open.code.learning.base.design121.decorator_mode.example01.decorator.Sugar;
import org.open.code.learning.base.design121.decorator_mode.example01.specific.Americano;

/**
 * 实现一个咖啡订单系统，基础咖啡有两种：美式咖啡（30 元）、拿铁（40 元）。可添加的配料有：牛奶（5 元）、糖（3 元）、奶泡（4 元）。
 * 要求使用装饰者模式，使得可以任意组合配料，并能计算总价和描述订单。
 * 提示：
 * 抽象组件：Coffee（包含getPrice()和getDescription()方法）
 * 具体组件：Americano、Latte
 * 抽象装饰器：CondimentDecorator
 * 具体装饰器：Milk、Sugar、Foam
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class ExampleMain01 {
    public static void main(String[] args) {

        //相同结果不同写法：写法一
        Coffee coffee = new Sugar(new Milk(new Americano()));
        System.out.println(coffee.getDescription());
        System.out.println(coffee.getPrice());


        //相同结果不同写法：写法二
        Coffee coffee01 = new Americano();
        coffee01 = new Milk(coffee01);
        coffee01 = new Sugar(coffee01);

        System.out.printf("订单：%s，总价：%d元%n",
                coffee01.getDescription(),
                coffee01.getPrice());  // 输出：订单：美式咖啡 + 牛奶 + 糖，总价：38元

    }
}
