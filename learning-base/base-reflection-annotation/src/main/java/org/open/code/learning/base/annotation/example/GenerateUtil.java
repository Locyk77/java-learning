package org.open.code.learning.base.annotation.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 *@author: Locyk
 *@time: 2025/9/11
 *
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface GenerateUtil {
    String suffix() default "Util";//生成的工具类后缀
}
