package org.open.code.learning.collection.concurrenthashmap;

import lombok.Data;

/**
 *
 *@author: Locyk
 *@time: 2025/11/12
 *
 */
@Data
public class User {
    private String name;
    private int loginCount;

    public User(String name) {
        this.name = name;
        this.loginCount = 0;
    }
}
