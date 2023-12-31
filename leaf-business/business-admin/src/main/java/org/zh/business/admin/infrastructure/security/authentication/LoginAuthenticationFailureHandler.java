package org.zh.business.admin.infrastructure.security.authentication;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.zh.business.admin.common.constant.AuthConstants;
import org.zh.tech.auth.filter.ResolvableExceptionAuthenticationFailureHandler;
import org.zh.thch.common.restful.RestfulResult;
import org.zh.thch.common.restful.ResultEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/13 14:28:31
 */

@Component
public class LoginAuthenticationFailureHandler extends ResolvableExceptionAuthenticationFailureHandler {
    @Override
    protected Object getFailureResult(HttpServletRequest request, AuthenticationException exception) {
        if (exception instanceof UsernameNotFoundException) {
            return RestfulResult.failure(AuthConstants.USER_NOT_EXIST_CODE, exception.getMessage());
        } else if (exception instanceof BadCredentialsException) {
            return RestfulResult.failure(AuthConstants.PWD_WRONG_CODE, exception.getMessage());
        } else if (exception instanceof DisabledException) {
            return RestfulResult.failure(AuthConstants.USER_DISABLED_CODE, exception.getMessage());
        } else if (exception instanceof LockedException) {
            return RestfulResult.failure(AuthConstants.USER_LOCKED_CODE, exception.getMessage());
        }
        return RestfulResult.failure(ResultEnum.UNKNOWN.getCode(), ResultEnum.UNKNOWN.getMessage());
    }
}
