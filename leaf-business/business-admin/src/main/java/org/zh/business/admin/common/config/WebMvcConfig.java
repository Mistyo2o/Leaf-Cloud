package org.zh.business.admin.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zh.tech.core.jwt.JwtParser;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/21 13:14:50
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public JwtParser internalJwtResolver() {
        return new JwtParser();
    }
}
