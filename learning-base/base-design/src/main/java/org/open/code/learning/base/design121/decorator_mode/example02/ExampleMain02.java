package org.open.code.learning.base.design121.decorator_mode.example02;

import org.open.code.learning.base.design121.decorator_mode.example02.decorator.PrefixSuffixDecorator;
import org.open.code.learning.base.design121.decorator_mode.example02.decorator.ReverseDecorator;
import org.open.code.learning.base.design121.decorator_mode.example02.decorator.UpperCaseDecorator;
import org.open.code.learning.base.design121.decorator_mode.example02.specific.PlainText;

/**
 * 实现文本格式化工具，基础文本为原始字符串。可添加的格式化功能包括：
 * 大写转换（将文本转为全大写）
 * 反转（将文本字符顺序反转）
 * 加前缀 / 后缀（如添加[INFO] 前缀和 --end后缀）
 * 要求使用装饰者模式，支持任意组合格式化，并能展示最终格式化结果。
 * 提示：
 * 抽象组件：Text（包含format()方法）
 * 具体组件：PlainText（原始文本）
 * 抽象装饰器：TextDecorator
 * 具体装饰器：UpperCaseDecorator、ReverseDecorator、PrefixSuffixDecorator（需通过构造函数传入前缀和后缀参数）
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class ExampleMain02 {
    public static void main(String[] args) {
        Text text = new PlainText("hello");
        text = new UpperCaseDecorator(text);
        text = new ReverseDecorator(text);
        System.out.println(text.format()); // 输出：OLLEH

        // 多重装饰组合
        Text text2 = new PlainText("test");
        text2 = new PrefixSuffixDecorator(text2, "[INFO] ", " --end");
        text2 = new UpperCaseDecorator(text2);
        System.out.println(text2.format());  // 输出：[INFO] TEST --END
    }
}
