package org.zh.tech.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zh.tech.auth.jwt.JwtAuthenticationFilter;

/**
 * @author zhouHui
 * @description web安全配置适配器
 */
@EnableWebSecurity
public abstract class WebSecurityConfigurerSupport extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //登录接口 允许匿名访问
                .antMatchers("**/login").anonymous()
                //其他请求都需要认证
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(getApplicationContext()), UsernamePasswordAuthenticationFilter.class);


    }


    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager();
    }


}
