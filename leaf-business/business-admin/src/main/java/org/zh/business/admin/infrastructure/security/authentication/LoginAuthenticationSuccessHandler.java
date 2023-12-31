package org.zh.business.admin.infrastructure.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.zh.tech.auth.filter.AjaxAuthenticationSuccessHandler;
import org.zh.tech.auth.user.DefaultUserSpecificDetails;
import org.zh.tech.auth.user.UserSpecificDetails;
import org.zh.tech.core.jwt.JwtParser;
import org.zh.thch.common.restful.RestfulResult;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/13 14:31:14
 */

@Component
public class LoginAuthenticationSuccessHandler extends AjaxAuthenticationSuccessHandler {

    @Autowired
    private JwtParser jwtParser;

    public static Function<Authentication, Object> GET_DETAIL_FUNCTION = Authentication::getDetails;
    @Override
    protected Object getAjaxLoginResult(HttpServletRequest request, Authentication authentication) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication contextAuthentication = securityContext.getAuthentication();
        if (contextAuthentication != null){
            Object details  = GET_DETAIL_FUNCTION.apply(contextAuthentication);
            if (details instanceof UserSpecificDetails) {
                DefaultUserSpecificDetails userSpecificDetails = (DefaultUserSpecificDetails) details;
                String token = jwtParser.generateToken(userSpecificDetails.getUsername());
                return RestfulResult.success(token);
            }
        }
        return RestfulResult.failure();
    }
}
