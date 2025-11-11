package org.open.code.learning.base.config;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import org.open.code.learning.base.handler.ISecureHandler;
import org.open.code.learning.base.props.BasicSecure;
import org.open.code.learning.base.props.CloudSecureProperties;
import org.open.code.learning.base.props.SignSecure;
import org.open.code.learning.base.registry.SecureRegistry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *@author: Locyk
 *@time: 2025/10/29
 *
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({CloudSecureProperties.class})
public class SecureConfiguration implements WebMvcConfigurer {

    private final SecureRegistry secureRegistry;

    private final ISecureHandler secureHandler;

    private final CloudSecureProperties secureProperties;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        // 设置基础认证授权
        if (secureRegistry.isBasicEnabled()) {
            List<BasicSecure> basicSecures = this.secureRegistry.addBasicPatterns(secureProperties.getBasic()).getBasicSecures();
            if (!basicSecures.isEmpty()) {
                registry.addInterceptor(secureHandler.basicInterceptor());
                // 设置路径放行
                secureRegistry.excludePathPatterns(basicSecures.stream().map(BasicSecure::getPattern).collect(Collectors.toList()));
            }
        }
        // 设置签名认证授权
        if (secureRegistry.isSignEnabled() || secureProperties.getSignEnabled()) {
            List<SignSecure> signSecures = this.secureRegistry.addSignPatterns(secureProperties.getSign()).getSignSecures();
            if (!signSecures.isEmpty()) {
                registry.addInterceptor(secureHandler.signInterceptor());
                // 设置路径放行
                secureRegistry.excludePathPatterns(signSecures.stream().map(SignSecure::getPattern).collect(Collectors.toList()));
            }
        }
        // 设置客户端授权
        if (secureRegistry.isClientEnabled() || secureProperties.getClientEnabled()) {
            secureProperties.getClient().forEach(
                    clientSecure -> registry.addInterceptor(secureHandler.clientInterceptor())
                            .addPathPatterns(clientSecure.getPathPatterns())
            );
        }

        // 设置令牌严格模式
        if (!secureRegistry.isStrictToken()) {
            secureProperties.setStrictToken(false);
        }
        // 设置请求头严格模式
        if (!secureRegistry.isStrictHeader()) {
            secureProperties.setStrictHeader(false);
        }

        // 设置路径放行
        if (secureRegistry.isEnabled() || secureProperties.getEnabled()) {
            InterceptorRegistration interceptorRegistration = registry.addInterceptor(secureHandler.tokenInterceptor())
                    .excludePathPatterns(secureRegistry.getExcludePatterns())
                    .excludePathPatterns(secureRegistry.getDefaultExcludePatterns())
                    .excludePathPatterns(secureProperties.getSkipUrl());
            // 宽松模式下获取放行路径且再新建一套自定义放行路径，用于处理cloud网关虚拟路径导致未匹配的问题
            // 严格模式下不予处理，应严格按照cloud和boot的路由进行匹配
            if (!secureProperties.getStrictToken()) {
                interceptorRegistration.excludePathPatterns(secureProperties.getSkipUrl().stream()
                        .map(url -> removePrefix(url, "/base-inter")).toList());
            }
        }
    }

    private String removePrefix(CharSequence str, CharSequence prefix) {
        if (ObjectUtil.isEmpty(str) || ObjectUtil.isEmpty(prefix)) {
            return "";
        }

        final String str2 = str.toString();
        if (str2.startsWith(prefix.toString())) {
            return subSuf(str2, prefix.length());
        }
        return str2;
    }

    private String subSuf(CharSequence string, int fromIndex) {
        if (ObjectUtil.isEmpty(string)) {
            return null;
        }
        return sub(string, fromIndex, string.length());
    }

    private String sub(CharSequence str, int fromIndex, int toIndex) {
        if (ObjectUtil.isEmpty(str)) {
            return "";
        }
        int len = str.length();

        if (fromIndex < 0) {
            fromIndex = len + fromIndex;
            if (fromIndex < 0) {
                fromIndex = 0;
            }
        } else if (fromIndex > len) {
            fromIndex = len;
        }

        if (toIndex < 0) {
            toIndex = len + toIndex;
            if (toIndex < 0) {
                toIndex = len;
            }
        } else if (toIndex > len) {
            toIndex = len;
        }

        if (toIndex < fromIndex) {
            int tmp = fromIndex;
            fromIndex = toIndex;
            toIndex = tmp;
        }

        if (fromIndex == toIndex) {
            return "";
        }

        return str.toString().substring(fromIndex, toIndex);
    }
}
