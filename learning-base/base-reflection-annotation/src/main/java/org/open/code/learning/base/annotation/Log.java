package org.open.code.learning.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法级别的注解：用于日志记录
 *
 *@author: Locyk
 *@time: 2025/9/10
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    boolean needLog() default true; // 是否需要记录日志
    String description() default ""; // 日志描述
}
