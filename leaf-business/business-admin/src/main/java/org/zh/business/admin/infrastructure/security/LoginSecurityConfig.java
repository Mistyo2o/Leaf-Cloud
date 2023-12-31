package org.zh.business.admin.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.zh.business.admin.infrastructure.security.authentication.LoginAuthenticationProvider;
import org.zh.tech.auth.authentication.DefaultAuthenticationTokenResolver;
import org.zh.tech.auth.authentication.SmsAuthenticationTokenResolver;
import org.zh.tech.auth.config.LoginSecurityConfigurerSupport;
import org.zh.tech.auth.filter.LoginModeAuthenticationFilter;
import org.zh.thch.common.basic.LoginModeEnum;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/9 13:45:45
 */
@Component
public class LoginSecurityConfig extends LoginSecurityConfigurerSupport<UsernamePasswordAuthenticationFilter, LoginAuthenticationProvider> {

    @Bean
    public DefaultAuthenticationTokenResolver defaultAuthenticationTokenResolver() {
        return new DefaultAuthenticationTokenResolver(LoginModeEnum.ACCOUNT.getValue());
    }

    @Bean
    public SmsAuthenticationTokenResolver smsAuthenticationTokenResolver() {
        return new SmsAuthenticationTokenResolver(LoginModeEnum.SMS.getValue());
    }

    @Override
    public UsernamePasswordAuthenticationFilter createProcessingFilter() {
        return new LoginModeAuthenticationFilter(super.getApplicationContext());
    }
}
