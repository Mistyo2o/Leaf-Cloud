package org.zh.tech.auth.authentication;

/**
 * @author zhouHui
 * @version 1.0
 * @description 短信认证
 * @date 2023/12/9 16:02:16
 */
public class SmsVerifyCodeAuthenticationToken extends UnauthenticatedAuthenticationToken{

    public SmsVerifyCodeAuthenticationToken(Object cellphone, Object verifyCode) {
        super(cellphone, verifyCode);
    }

    public String getCellphone() {
        return (String) getPrincipal();
    }

    public String getVerifyCode() {
        return (String) getCredentials();
    }
}
