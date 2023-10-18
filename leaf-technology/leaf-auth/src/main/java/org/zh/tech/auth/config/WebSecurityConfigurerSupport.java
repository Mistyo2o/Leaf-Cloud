package org.zh.tech.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zh.tech.auth.jwt.JwtAuthenticationFilter;

import java.util.Collection;

/**
 * @author zhouHui
 * @description web安全配置适配器
 */
@EnableWebSecurity
public abstract class WebSecurityConfigurerSupport extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 覆盖父类的方法实现，且不调用父类方法实现，以标记AuthenticationManager由自定义创建，避免创建多个实例
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
                .addFilterAfter(new JwtAuthenticationFilter(getApplicationContext()), UsernamePasswordAuthenticationFilter.class);

        Collection<SecurityConfigurerAdapter> configurers = getSecurityConfigurerAdapters();
        for (SecurityConfigurerAdapter configurer : configurers) {
            http.apply(configurer);
        }
    }


    @SuppressWarnings({ "rawtypes" })
    protected Collection<SecurityConfigurerAdapter> getSecurityConfigurerAdapters() {
        return getApplicationContext().getBeansOfType(SecurityConfigurerAdapter.class).values();
    }

}
