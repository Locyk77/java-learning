package org.open.code.learning.base.config;

import org.open.code.learning.base.handler.CloudSecureHandler;
import org.open.code.learning.base.handler.ISecureHandler;
import org.open.code.learning.base.registry.SecureRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *@author: Locyk
 *@time: 2025/10/29
 *
 */
@Configuration
public class RegistryConfiguration {
    @Bean
    @ConditionalOnMissingBean(SecureRegistry.class)
    public SecureRegistry secureRegistry() {
        return new SecureRegistry();
    }


    @Bean
    @ConditionalOnMissingBean(ISecureHandler.class)
    public ISecureHandler secureHandler() {
        return new CloudSecureHandler();
    }

}
