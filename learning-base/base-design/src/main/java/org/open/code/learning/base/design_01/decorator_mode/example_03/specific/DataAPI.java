package org.open.code.learning.base.design_01.decorator_mode.example_03.specific;

import org.open.code.learning.base.design_01.decorator_mode.example_03.API;

/**
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class DataAPI implements API {

    @Override
    public String invoke() throws SecurityException {
        return "返回敏感数据：xxx-xxx-xxx";
    }
}
