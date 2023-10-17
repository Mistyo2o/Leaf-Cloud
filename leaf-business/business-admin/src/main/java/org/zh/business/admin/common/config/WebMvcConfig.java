package org.zh.business.admin.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zh.tech.auth.jwt.DefaultInternalJwtResolver;
import org.zh.tech.auth.jwt.JwtResolver;

/**
 * @author zhouHui
 * @description WebMvc配置器
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    @ConditionalOnMissingBean(JwtResolver.class)
    public JwtResolver internalJwtResolver() {
        return new DefaultInternalJwtResolver();
    }
}
