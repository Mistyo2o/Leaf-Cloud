package org.zh.business.admin.security.authentication;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.zh.tech.core.jwt.JwtParser;
import org.zh.thch.common.basic.WebConstants;
import org.zh.thch.common.restful.RestfulResult;
import org.zh.thch.common.util.JsonUtil;
import org.zh.thch.common.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 配置登出成功处理器
 **/

@Component
public class LogoutAuthenticationSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Autowired
    private JwtParser jwtParser;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String jwt = request.getHeader(WebConstants.HEADER_AUTH_JWT);
        if (jwt != null && jwt.startsWith("jwt:")) {
            //校验jwt
            String username = "";
            try {
                username = jwtParser.extractUsername(jwt.substring(4));
            }catch (Exception e){
                LogUtil.error(getClass(), e);
            }
            if (StringUtils.isNotBlank(username)){
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.getWriter().print(JsonUtil.toJson(RestfulResult.success()));
                return;
            }
        }

        super.handle(request, response, authentication);

    }
}
