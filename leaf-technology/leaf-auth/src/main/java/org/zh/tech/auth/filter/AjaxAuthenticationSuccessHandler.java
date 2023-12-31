package org.zh.tech.auth.filter;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.zh.thch.common.util.JsonUtil;
import org.zh.thch.common.util.WebHttpUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/13 14:19:10
 */
public abstract class AjaxAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public final void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        if (WebHttpUtil.isAjaxRequest(request)) {
            Object result = getAjaxLoginResult(request, authentication);
            if (result != null) {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.getWriter().print(JsonUtil.toJson(result));
            }
            // 清除当前线程的SecurityContext，使用一次性授权token交互认证
            SecurityContextHolder.clearContext();
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    protected abstract Object getAjaxLoginResult(HttpServletRequest request, Authentication authentication);
}
