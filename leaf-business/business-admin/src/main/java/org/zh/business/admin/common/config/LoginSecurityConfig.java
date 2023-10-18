package org.zh.business.admin.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.zh.tech.auth.authentication.DefaultAuthenticationTokenResolver;
import org.zh.tech.auth.config.LoginSecurityConfigurerSupport;
import org.zh.tech.auth.filter.LoginModeAuthenticationFilter;
import org.zh.thch.common.basic.LoginModeEnum;

/**
 * @author: zh
 * @date: 2023/10/18 9:12
 */

@Component
public class LoginSecurityConfig extends LoginSecurityConfigurerSupport<UsernamePasswordAuthenticationFilter, LoginAuthenticationProvider> {

    @Bean
    public DefaultAuthenticationTokenResolver defaultAuthenticationTokenResolver() {
        return new DefaultAuthenticationTokenResolver(LoginModeEnum.ACCOUNT.getValue());
    }

    @Override
    protected UsernamePasswordAuthenticationFilter createProcessingFilter() {
        return new LoginModeAuthenticationFilter(super.getApplicationContext());
    }
}
