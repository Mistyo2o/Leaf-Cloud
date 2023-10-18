package org.zh.tech.auth.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @author: zh
 * @date: 2023/10/18 10:00
 * @description:
 */
public abstract class AbstractAuthenticationTokenResolver<T extends AbstractAuthenticationToken> implements AuthenticationTokenResolver<T> {
    private String loginMode;

    public AbstractAuthenticationTokenResolver(String loginMode) {
        this.loginMode = loginMode;
    }

    @Override
    public String getLoginMode() {
        return this.loginMode;
    }
}
