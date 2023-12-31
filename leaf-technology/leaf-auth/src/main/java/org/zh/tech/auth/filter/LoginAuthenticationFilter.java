package org.zh.tech.auth.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zh.thch.common.meta.ApiMetaProperties;
import org.zh.thch.common.util.SpringUtil;


/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/7 16:32:05
 */
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public LoginAuthenticationFilter(ApplicationContext context) {
        AjaxAuthenticationSuccessHandler successHandler = SpringUtil.getFirstBeanByClass(context,
                AjaxAuthenticationSuccessHandler.class);
        if (successHandler != null) {
            setAuthenticationSuccessHandler(successHandler);
        }
        applySuccessTargetUrlParameter(context, getSuccessHandler());

        ResolvableExceptionAuthenticationFailureHandler failureHandler = SpringUtil
                .getFirstBeanByClass(context, ResolvableExceptionAuthenticationFailureHandler.class);
        if (failureHandler != null) {
            setAuthenticationFailureHandler(failureHandler); // 指定登录失败时的处理器
        }
    }

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

    @Override
    public AuthenticationSuccessHandler getSuccessHandler() {
        return super.getSuccessHandler();
    }

    @Override
    public AuthenticationFailureHandler getFailureHandler() {
        return super.getFailureHandler();
    }

}
