package org.open.code.learning.base.design_01.decorator_mode.example_02.specific;

import org.open.code.learning.base.design_01.decorator_mode.example_02.Text;

/**
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class PlainText implements Text {

    private String content;

    public PlainText(String content) {
        this.content = content;
    }
    @Override
    public String format() {
        return content;
    }
}
