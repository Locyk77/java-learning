package org.open.code.learning.base.annotation;

/**
 * 使用自定义注解的实体类
 *
 *@author: Locyk
 *@time: 2025/9/10
 *
 */
@ServiceInfo(name = "用户服务", author = "张三")
public class UserService {
    @Validate(notNull = true, minLength = 2)
    private String username;

    @Validate(notNull = true)
    private Integer age;

    public UserService(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    @Log(description = "获取用户信息")
    public String getUserInfo() {
        return "用户名：" + username + "，年龄：" + age;
    }

    @Log(needLog = false)
    public void updatePassword(String newPassword) {
        System.out.println("密码已更新");
    }
}
