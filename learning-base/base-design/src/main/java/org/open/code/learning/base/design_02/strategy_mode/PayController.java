package org.open.code.learning.base.design_02.strategy_mode;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 1-2-2、应用设计模式解决实际问题
 * 策略模式
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */

@RestController
@RequestMapping("/pay")
@AllArgsConstructor
public class PayController {

    private final PaymentService paymentService;

    @GetMapping("/strategy")
    public String pay(@RequestParam String type, @RequestParam BigDecimal amount) {
        return paymentService.processPayment(type, amount);
    }
}
