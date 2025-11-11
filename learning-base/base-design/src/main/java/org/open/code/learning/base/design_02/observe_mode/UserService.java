package org.open.code.learning.base.design_02.observe_mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
@Service
public class UserService {
    // Spring事件发布器
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    // 注册用户并发布事件
    public void register(String userName) {
        // 保存用户逻辑...
        System.out.println("用户注册成功：" + userName);

        // 发布事件
        eventPublisher.publishEvent(new UserRegisteredEvent(userName));
    }
}
