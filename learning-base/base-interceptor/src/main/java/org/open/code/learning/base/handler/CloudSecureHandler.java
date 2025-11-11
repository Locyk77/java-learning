package org.open.code.learning.base.handler;

import org.open.code.learning.base.interceptor.BasicInterceptor;
import org.open.code.learning.base.interceptor.ClientInterceptor;
import org.open.code.learning.base.interceptor.SignInterceptor;
import org.open.code.learning.base.interceptor.TokenInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 *
 *@author: Locyk
 *@time: 2025/10/29
 *
 */
public class CloudSecureHandler implements ISecureHandler {
    @Override
    public HandlerInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }

    @Override
    public HandlerInterceptor basicInterceptor() {
        return new BasicInterceptor();
    }

    @Override
    public HandlerInterceptor signInterceptor() {
        return new SignInterceptor();
    }

    @Override
    public HandlerInterceptor clientInterceptor() {
        return new ClientInterceptor();
    }
}
