package org.open.code.learning.base.design121.decorator_mode.example03;

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

    public String invoke() throws SecurityException {
        return api.invoke();
    }
}
