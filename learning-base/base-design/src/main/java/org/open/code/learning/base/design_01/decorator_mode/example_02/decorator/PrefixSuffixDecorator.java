package org.open.code.learning.base.design_01.decorator_mode.example_02.decorator;

import org.open.code.learning.base.design_01.decorator_mode.example_02.Text;
import org.open.code.learning.base.design_01.decorator_mode.example_02.TextDecorator;

/**
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class PrefixSuffixDecorator extends TextDecorator {

    private String prefix;
    private String suffix;

    public PrefixSuffixDecorator(Text text, String prefix, String suffix) {
        super(text);
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public String format() {
        String result = super.format();
        return prefix + result + suffix;
    }
}
