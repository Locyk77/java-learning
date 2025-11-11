package org.open.code.learning.base.design121.decorator_mode.example02.decorator;

import org.open.code.learning.base.design121.decorator_mode.example02.Text;
import org.open.code.learning.base.design121.decorator_mode.example02.TextDecorator;

/**
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class ReverseDecorator extends TextDecorator {
    public ReverseDecorator(Text text) {
        super(text);
    }

    @Override
    public String format() {
        String result = super.format();
        char[] chars = result.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = chars.length - 1; i >= 0; i--) {
            sb.append(chars[i]);
        }
        return sb.toString();
    }


}
