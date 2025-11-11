package org.open.code.learning.base.design_01.decorator_mode.example_03;

/**
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public abstract class APIDecorator implements API {
    protected API api;

    public APIDecorator(API api) {
        this.api = api;
    }

    @Override
    public String invoke() throws SecurityException {
        return api.invoke();
    }
}
