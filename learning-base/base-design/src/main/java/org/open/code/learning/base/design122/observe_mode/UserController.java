package org.open.code.learning.base.design122.observe_mode;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
@RestController
@RequestMapping("/register")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public void register(@RequestParam String userName) {
        userService.register(userName);
    }
}
