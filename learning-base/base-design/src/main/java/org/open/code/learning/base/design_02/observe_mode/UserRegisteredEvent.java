package org.open.code.learning.base.design_02.observe_mode;

import org.springframework.context.ApplicationEvent;

/**
 * 用户注册事件（被观察者状态）
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class UserRegisteredEvent extends ApplicationEvent {
    private String userName;

    public UserRegisteredEvent(String userName) {
        super(userName);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
