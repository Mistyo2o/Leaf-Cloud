package org.zh.tech.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zh.thch.common.util.ClassUtil;

import static cn.hutool.extra.spring.SpringUtil.getApplicationContext;

/**
 * 登录安全配置器支持
 *
 * @param <F> 处理过滤器类型
 * @param <P> 认证提供器类型
 */
public abstract class LoginSecurityConfigurerSupport<F extends AbstractAuthenticationProcessingFilter, P extends AuthenticationProvider>
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private ApplicationContext context;
    private F processingFilter;

    protected final ApplicationContext getApplicationContext() {
        return this.context;
    }

    @Override
    public final void init(HttpSecurity builder) throws Exception {
        builder.authenticationProvider(getAuthenticationProvider());
    }

    @Override
    public final void configure(HttpSecurity builder) throws Exception {
        this.processingFilter = createProcessingFilter();
        if (this.processingFilter != null) {
            this.processingFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class)); // 固定必须
            configure(builder, this.processingFilter);
        }
    }


    //获取认证提供者类
    protected P getAuthenticationProvider() {
        Class<P> type = ClassUtil.getActualGenericType(getClass(), 1); // 取第二个泛型：认证提供器类型
        assert type != null;
        return getApplicationContext().getBean(type);
    }


    /**
     * 创建处理过滤器<br>
     * 注意：该过滤器创建后还需经过框架的后续配置才可使用，所以不能通过从它处注入后获取返回
     *
     * @return 处理过滤器
     */
    protected abstract F createProcessingFilter();

    public F getProcessingFilter() {
        return this.processingFilter;
    }

    protected void configure(HttpSecurity http, F filter) {
        if (filter instanceof UsernamePasswordAuthenticationFilter) {
            http.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);
        } else {
            http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        }
    }
}