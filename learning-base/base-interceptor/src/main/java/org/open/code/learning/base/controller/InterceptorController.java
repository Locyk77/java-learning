package org.open.code.learning.base.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *@author: Locyk
 *@time: 2025/10/29
 *
 */
@RestController
@RequestMapping("/demo")
public class InterceptorController {


    /**
     * TODO skip-url
     * BasicInterceptor preHandle -> SignInterceptor preHandle
     * @return
     */
    @GetMapping("/skip/info")
    public String info1() {
        return "skip info";
    }

    /**
     * TODO basic
     * BasicInterceptor preHandle -> SignInterceptor preHandle
     * @return
     */
    @GetMapping("/basic/info")
    public String info2() {
        return "basic info";
    }

    /**
     * TODO sign
     * BasicInterceptor preHandle -> SignInterceptor preHandle
     * @return
     */
    @GetMapping("/sign/info")
    public String info3() {
        return "sign info";
    }

    /**
     * TODO client
     * BasicInterceptor preHandle -> SignInterceptor preHandle -> ClientInterceptor preHandle -> TokenInterceptor preHandle
     * @return
     */
    @GetMapping("/client/info")
    public String info4() {
        return "client info";
    }

    /**
     * BasicInterceptor preHandle-> SignInterceptor preHandle -> TokenInterceptor preHandle
     * @return
     */
    @GetMapping("/test/info")
    public String info5() {
        return "test info";
    }
}
