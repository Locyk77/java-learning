package org.open.code.learning.base.design122.strategy_mode;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 支付服务（策略上下文）
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
@Service
public class PaymentService {
    // Spring自动注入所有PaymentStrategy实现
    private final Map<String, PaymentStrategy> strategyMap;

    // 构造函数注入，自动将所有PaymentStrategy bean放入map
    public PaymentService(List<PaymentStrategy> strategies) {
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(
                        PaymentStrategy::getType,
                        Function.identity()
                ));
    }

    // 执行支付
    public String processPayment(String type, BigDecimal amount) {
        PaymentStrategy strategy = strategyMap.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的支付方式：" + type);
        }
        return strategy.pay(amount);
    }
}
