package org.zh.tech.auth.authentication;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/9 16:04:01
 */
public class DefaultAuthenticationToken extends UnauthenticatedAuthenticationToken{

    public DefaultAuthenticationToken(Object username, Object password) {
        super(username, password);
    }

    public String getUsername(){
        return (String) getPrincipal();
    }

    public String getPassword(){
        return (String) getCredentials();
    }

}
