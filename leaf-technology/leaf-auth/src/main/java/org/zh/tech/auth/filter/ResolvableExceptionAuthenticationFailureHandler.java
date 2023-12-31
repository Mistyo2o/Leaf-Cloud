package org.zh.tech.auth.filter;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.zh.thch.common.basic.exception.ExceptionEnum;
import org.zh.thch.common.restful.RestfulResult;
import org.zh.thch.common.util.JsonUtil;
import org.zh.thch.common.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/13 14:24:59
 */
public abstract class ResolvableExceptionAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Object result = getFailureResult(request, exception);
        if (result != null) {
            response.getWriter().print(JsonUtil.toJson(result));
        } else {
            LogUtil.error(getClass(), exception); // 未知登录认证异常打印，方便后续追溯
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(JsonUtil.toJson(RestfulResult.getFailure(ExceptionEnum.UNAUTHORIZED)));
        }
    }

    protected abstract Object getFailureResult(HttpServletRequest request, AuthenticationException exception);
}
