package org.zh.tech.auth.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.zh.thch.common.meta.ApiMetaProperties;
import org.zh.thch.common.util.SpringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhouHui
 * @description 登录认证过滤器
 */
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    //设置登录成功跳转地址
    public static void applySuccessTargetUrlParameter(ApplicationContext context,
                                                      AuthenticationSuccessHandler successHandler) {
        if (successHandler instanceof AbstractAuthenticationTargetUrlRequestHandler) {
            ApiMetaProperties apiMetaProperties = SpringUtil.getFirstBeanByClass(context, ApiMetaProperties.class);
            if (apiMetaProperties != null) {
                String successTargetUrlParameter = apiMetaProperties.getRedirectTargetUrlParameter();
                if (StringUtils.isNotBlank(successTargetUrlParameter)) {
                    ((AbstractAuthenticationTargetUrlRequestHandler) successHandler)
                            .setTargetUrlParameter(successTargetUrlParameter);
                }
            }
        }
    }

    //设置登录成功 登录失败处理类
    public LoginAuthenticationFilter(ApplicationContext context) {
        System.out.println(context);
//        AjaxAuthenticationSuccessHandler successHandler = SpringUtil.getFirstBeanByClass(context,
//                AjaxAuthenticationSuccessHandler.class);
//        if (successHandler != null) {
//            setAuthenticationSuccessHandler(successHandler);
//        }
//        applySuccessTargetUrlParameter(context, getSuccessHandler());
//
//        ResolvableExceptionAuthenticationFailureHandler failureHandler = SpringUtil
//                .getFirstBeanByClass(context, ResolvableExceptionAuthenticationFailureHandler.class);
//        if (failureHandler != null) {
//            setAuthenticationFailureHandler(failureHandler); // 指定登录失败时的处理器
//        }
    }

    @Override
    public AuthenticationSuccessHandler getSuccessHandler() {
        return super.getSuccessHandler();
    }

    @Override
    public AuthenticationFailureHandler getFailureHandler() {
        return super.getFailureHandler();
    }
}
