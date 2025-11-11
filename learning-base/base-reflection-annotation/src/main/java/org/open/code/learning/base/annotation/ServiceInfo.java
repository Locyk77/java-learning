package org.open.code.learning.base.annotation;

import java.lang.annotation.*;

/**
 * 类级别的注解：用于描述服务信息
 *
 *@author: Locyk
 *@time: 2025/9/10
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceInfo {
    String name(); // 服务名称

    String version() default "1.0.0"; // 版本号，默认1.0.0

    String author(); // 作者
}
