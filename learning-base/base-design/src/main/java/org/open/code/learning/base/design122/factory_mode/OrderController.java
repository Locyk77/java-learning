package org.open.code.learning.base.design122.factory_mode;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/factory")
    public void order(@RequestBody Order order) {
        orderService.handleOrder(order);
    }
}
