package org.zh.tech.auth.authentication;

import org.zh.thch.common.basic.Strings;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/9 16:10:32
 */
public class SmsAuthenticationTokenResolver extends AuthenticationTokenResolverImpl<SmsVerifyCodeAuthenticationToken>{

    public SmsAuthenticationTokenResolver(String loginMode) {
        super(loginMode);
    }

    @Override
    public SmsVerifyCodeAuthenticationToken resolveAuthenticationToken(HttpServletRequest request) {
        String cellphone = request.getParameter("cellphone");
        String verifyCode = request.getParameter("verifyCode");
        if (cellphone == null) {
            cellphone = Strings.EMPTY;
        }
        cellphone = cellphone.trim();
        if (verifyCode == null) {
            verifyCode = Strings.EMPTY;
        }
        verifyCode = verifyCode.trim();
        return new SmsVerifyCodeAuthenticationToken(cellphone, verifyCode);
    }
}
