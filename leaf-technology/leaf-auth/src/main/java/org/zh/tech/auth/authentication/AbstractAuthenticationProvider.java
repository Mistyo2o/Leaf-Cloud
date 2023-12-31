package org.zh.tech.auth.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.zh.thch.common.util.ClassUtil;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/9 14:20:11
 */
public abstract class AbstractAuthenticationProvider<A extends Authentication> implements AuthenticationProvider {

    @Override
    public boolean supports(Class<?> authentication) {
        Class<?> genericType = ClassUtil.getActualGenericType(getClass(), 0);
        return genericType != null && genericType.isAssignableFrom(authentication);
    }
}
