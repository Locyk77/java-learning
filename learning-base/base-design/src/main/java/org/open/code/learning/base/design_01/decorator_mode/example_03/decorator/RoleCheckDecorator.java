package org.open.code.learning.base.design_01.decorator_mode.example_03.decorator;

import org.open.code.learning.base.design_01.decorator_mode.example_03.API;
import org.open.code.learning.base.design_01.decorator_mode.example_03.APIDecorator;

import java.util.List;

/**
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class RoleCheckDecorator extends APIDecorator {
    private List<String> allowedRoles;
    private String userRole;

    public RoleCheckDecorator(API api,List<String> allowedRoles,String userRole) {
        super(api);
        this.allowedRoles = allowedRoles;
        this.userRole = userRole;
    }

    @Override
    public String invoke() throws SecurityException {
        if (!allowedRoles.contains(userRole)) {
            throw new SecurityException("权限校验失败：用户角色不允许访问");
        }
        return super.invoke();
    }
}
