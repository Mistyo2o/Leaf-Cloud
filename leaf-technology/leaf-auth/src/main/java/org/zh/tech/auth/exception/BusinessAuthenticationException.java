package org.zh.tech.auth.exception;

import org.springframework.security.core.AuthenticationException;
import org.zh.thch.common.basic.exception.BusinessException;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/9 16:39:43
 */
public class BusinessAuthenticationException extends AuthenticationException {
    private static final long serialVersionUID = -5571766088305286560L;

    public BusinessAuthenticationException(BusinessException cause) {
        super(cause.getMessage(), cause);
    }

    public BusinessAuthenticationException(String message, Object... args) {
        this(new BusinessException(message, args));
    }
}
