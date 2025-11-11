package org.open.code.learning.base.handler;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 *
 *@author: Locyk
 *@time: 2025/10/29
 *
 */
public interface ISecureHandler {

    /**
     * token拦截器
     *
     * @return tokenInterceptor
     */
    HandlerInterceptor tokenInterceptor();

    /**
     * basic拦截器
     *
     * @return HandlerInterceptor
     */
    HandlerInterceptor basicInterceptor();


    /**
     * sign拦截器
     *
     * @return HandlerInterceptor
     */
    HandlerInterceptor signInterceptor();

    /**
     * client拦截器
     *
     * @return clientInterceptor
     */
    HandlerInterceptor clientInterceptor();


}
