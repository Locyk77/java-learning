package org.open.code.learning.base.design122.observe_mode;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 优惠券监听器（观察者2）
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
@Component
public class CouponListener {
    @EventListener
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        String user = event.getUserName();
        System.out.println("向用户" + user + "赠送新人优惠券");
    }
}