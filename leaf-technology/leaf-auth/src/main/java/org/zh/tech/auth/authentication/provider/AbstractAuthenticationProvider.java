package org.zh.tech.auth.authentication.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.zh.thch.common.util.ClassUtil;

/**
 * @author zhouHui
 * @version 1.0
 * @description 认证方式抽象类
 * @date 2023/10/17 22:41:21
 */
public abstract class AbstractAuthenticationProvider <A extends Authentication> implements AuthenticationProvider {
    @Override
    public boolean supports(Class<?> authentication) {
        Class<?> genericType = ClassUtil.getActualGenericType(getClass(), 0);
        return genericType != null && genericType.isAssignableFrom(authentication);
    }
}
