package org.open.code.learning.base.design122.observe_mode;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 邮件发送监听器（观察者1）
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
@Component
public class WelcomeEmailListener {
    // 监听UserRegisteredEvent事件
    @EventListener
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        String user = event.getUserName();
        System.out.println("向" + user + "发送欢迎邮件");
    }
}