package org.open.code.learning.collection.concurrenthashmap;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *@author: Locyk
 *@time: 2025/11/12
 *
 */
public class UserLoginTask implements Runnable {
    private ConcurrentHashMap<String, User> userCache;
    private User user;

    public UserLoginTask(ConcurrentHashMap<String, User> userCache, User user) {
        this.userCache = userCache;
        this.user = user;
    }

    @Override
    public void run() {
        System.out.println("用户：" + user.getName() + " 登录成功");
        userCache.putIfAbsent(user.getName(), user);
        userCache.compute(user.getName(), (k, v) -> {
            v.setLoginCount(v.getLoginCount() + 1);
            return v;
        });
    }
}
