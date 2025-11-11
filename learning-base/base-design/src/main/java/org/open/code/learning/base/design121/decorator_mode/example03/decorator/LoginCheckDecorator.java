package org.open.code.learning.base.design121.decorator_mode.example03.decorator;

import org.open.code.learning.base.design121.decorator_mode.example03.API;
import org.open.code.learning.base.design121.decorator_mode.example03.APIDecorator;

/**
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class LoginCheckDecorator extends APIDecorator {
    private boolean isLogin;

    public LoginCheckDecorator(API api, boolean isLogin) {
        super(api);
        this.isLogin = isLogin;
    }

    @Override
    public String invoke() throws SecurityException {
        if (!isLogin) {
            throw new SecurityException("权限校验失败：用户未登录");
        }
        return super.invoke();
    }
}
