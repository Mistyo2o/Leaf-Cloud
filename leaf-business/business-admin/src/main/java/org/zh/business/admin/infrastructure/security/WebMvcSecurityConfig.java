package org.zh.business.admin.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zh.tech.auth.config.WebSecurityConfigurerSupport;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/6 15:15:34
 */
@Configuration
public class WebMvcSecurityConfig extends WebSecurityConfigurerSupport {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
