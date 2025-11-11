package org.open.code.learning.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段级别的注解：用于数据校验
 *
 *@author: Locyk
 *@time: 2025/9/10
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {
    boolean notNull() default false; // 是否非空

    int minLength() default 0; // 字符串最小长度
}
