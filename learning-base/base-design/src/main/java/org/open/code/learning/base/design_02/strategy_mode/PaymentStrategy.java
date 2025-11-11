package org.open.code.learning.base.design_02.strategy_mode;

import java.math.BigDecimal;

/**
 * 支付策略接口
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public interface PaymentStrategy {
    // 支付方法
    String pay(BigDecimal amount);

    // 支持的支付类型
    String getType();
}
