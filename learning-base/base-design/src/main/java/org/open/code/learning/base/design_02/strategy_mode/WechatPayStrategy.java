package org.open.code.learning.base.design_02.strategy_mode;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 微信支付实现
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
@Component
public class WechatPayStrategy implements PaymentStrategy {
    @Override
    public String pay(BigDecimal amount) {
        return "使用微信支付了：" + amount + "元";
    }

    @Override
    public String getType() {
        return "WECHAT";
    }
}
