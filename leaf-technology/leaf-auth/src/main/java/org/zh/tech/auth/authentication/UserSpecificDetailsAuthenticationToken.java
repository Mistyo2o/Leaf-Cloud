package org.zh.tech.auth.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.zh.tech.auth.user.UserSpecificDetails;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/9 18:25:50
 */
public class UserSpecificDetailsAuthenticationToken extends AbstractAuthenticationToken  {

    private static final long serialVersionUID = -7220717019759924639L;

    private String ip;

    public UserSpecificDetailsAuthenticationToken(UserSpecificDetails<?> details) {
        super(details.getAuthorities());
        super.setAuthenticated(true);
        setDetails(details);
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return ((UserSpecificDetails<?>) getDetails()).getIdentity();
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new UnsupportedOperationException();
    }
}
