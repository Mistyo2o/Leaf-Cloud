package org.zh.tech.auth.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/9 15:45:59
 */
public class UnauthenticatedAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 7340231278504806289L;

    //用户名
    private Object principal;
    //密码
    private Object credentials;

    public UnauthenticatedAuthenticationToken(Object principal, Object credentials) {
        super(null);
        super.setAuthenticated(false);
        this.principal = principal;
        this.credentials = credentials;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
