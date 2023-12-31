package org.zh.tech.auth.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/9 14:31:52
 */
public abstract class AuthenticationTokenResolverImpl<T extends AbstractAuthenticationToken> implements AuthenticationTokenResolver<T> {

    private String loginMode;

    public AuthenticationTokenResolverImpl(String loginMode) {
        this.loginMode = loginMode;
    }

    @Override
    public String getLoginMode() {
        return this.loginMode;
    }

}
