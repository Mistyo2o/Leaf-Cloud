package org.zh.business.admin.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zh.tech.auth.config.WebSecurityConfigurerSupport;

/**
 * @author zhouHui
 * Web安全配置器
 */

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerSupport {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
