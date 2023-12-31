package org.zh.tech.auth.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.zh.tech.auth.filter.JwtAuthenticationFilter;

import java.util.Collection;


/**
 * @author zhouHui
 * @description web安全配置适配器
 */
@EnableWebSecurity
public abstract class WebSecurityConfigurerSupport extends WebSecurityConfigurerAdapter {


    // 登出成功后的处理
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        SimpleUrlLogoutSuccessHandler handler = getApplicationContext().getBean(SimpleUrlLogoutSuccessHandler.class);
        if (handler == null) { // 未获取登出处理器则使用默认
            handler = new SimpleUrlLogoutSuccessHandler();
        }
        //重定向的地址
        //handler.setRedirectStrategy(this.redirectStrategy);
        //String logoutSuccessUrl = this.urlProvider.getLogoutSuccessUrl();
        handler.setDefaultTargetUrl("/logout");
        //handler.setTargetUrlParameter(this.apiMetaProperties.getRedirectTargetUrlParameter());
        return handler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 覆盖父类的方法实现，且不调用父类方法实现，以标记AuthenticationManager由自定义创建，避免创建多个实例
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void configure(HttpSecurity http) throws Exception {

        Collection<SecurityConfigurerAdapter> configurerAdapters = getSecurityConfigurerAdapters();
        for (SecurityConfigurerAdapter configurerAdapter : configurerAdapters) {
            http.apply(configurerAdapter);
        }

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //登录接口 允许匿名访问
                .antMatchers("/login").permitAll()
                //其他请求都需要认证
                .anyRequest().authenticated();
        http.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler()).deleteCookies(getLogoutClearCookies());
    }

    @SuppressWarnings({"rawtypes"})
    protected Collection<SecurityConfigurerAdapter> getSecurityConfigurerAdapters() {
        return getApplicationContext().getBeansOfType(SecurityConfigurerAdapter.class).values();
    }

    protected String[] getLogoutClearCookies() {
        return new String[] { "JSESSIONID", "SESSION" };
    }

}
