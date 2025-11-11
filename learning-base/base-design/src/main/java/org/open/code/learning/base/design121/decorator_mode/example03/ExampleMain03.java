package org.open.code.learning.base.design121.decorator_mode.example03;

import org.open.code.learning.base.design121.decorator_mode.example03.decorator.LoginCheckDecorator;
import org.open.code.learning.base.design121.decorator_mode.example03.decorator.RoleCheckDecorator;
import org.open.code.learning.base.design121.decorator_mode.example03.decorator.TimeLimitDecorator;
import org.open.code.learning.base.design121.decorator_mode.example03.specific.DataAPI;

import java.util.List;

/**
 * 实现一个 API 接口权限校验系统，基础接口为DataAPI（包含getData()方法，返回敏感数据）。需添加多层权限校验：
 * 登录校验（检查用户是否已登录）
 * 角色校验（检查用户是否有ADMIN角色）
 * 时间限制（仅允许工作日 9:00-18:00 访问）
 * 要求使用装饰者模式，支持动态组合校验规则，任何一层校验失败则抛出异常，否则返回数据。
 * 提示：
 * 抽象组件：API（包含invoke()方法）
 * 具体组件：DataAPI（核心数据接口）
 * 抽象装饰器：APIDecorator
 * 具体装饰器：LoginCheckDecorator、RoleCheckDecorator（需传入允许的角色列表）、TimeLimitDecorator（需传入允许的时间范围参数）
 *
 * TODO 扩展要求：支持校验顺序的动态调整（如先检查时间再检查登录） 暂未做
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class ExampleMain03 {
    public static void main(String[] args) {
        // 基础API
        API api = new DataAPI();

        // 组合装饰器：登录校验 → 角色校验 → 时间限制
        api = new LoginCheckDecorator(api, true);  // 用户已登录
        api = new RoleCheckDecorator(api, List.of("ADMIN", "MANAGER"), "ADMIN");  // 管理员角色
        api = new TimeLimitDecorator(api, 9, 18);  // 9:00-18:00允许访问
        try {
            String result = api.invoke();
            System.out.println("API调用成功：" + result);
        } catch (SecurityException e) {
            System.out.println(e.getMessage());
        }
    }
}
