package org.open.code.learning.base.design_01.decorator_mode.example_02.decorator;

import org.open.code.learning.base.design_01.decorator_mode.example_02.Text;
import org.open.code.learning.base.design_01.decorator_mode.example_02.TextDecorator;

/**
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class UpperCaseDecorator extends TextDecorator {
    public UpperCaseDecorator(Text text) {
        super(text);
    }

    @Override
    public String format() {
        String result = super.format();
        return result.toUpperCase();
    }
}
