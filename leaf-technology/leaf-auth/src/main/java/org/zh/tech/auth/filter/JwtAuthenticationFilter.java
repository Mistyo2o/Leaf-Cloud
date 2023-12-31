package org.zh.tech.auth.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zh.tech.auth.authentication.UserSpecificDetailsAuthenticationToken;
import org.zh.tech.auth.user.DefaultUserSpecificDetails;
import org.zh.tech.core.jwt.JwtParser;
import org.zh.thch.common.basic.WebConstants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/6 13:05:36
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        final String authHeader = request.getHeader(WebConstants.HEADER_AUTH_JWT);
        final String jwt;
        if (authHeader != null && authHeader.startsWith("jwt:")) {
            jwt = authHeader.substring(4);
            JwtParser jwtParser = new JwtParser();
            //token格式如 jwt:token
            String username = jwtParser.extractUsername(jwt);
            if (!StringUtils.isBlank(username)) {
                DefaultUserSpecificDetails defaultUserSpecificDetails = new DefaultUserSpecificDetails();
                defaultUserSpecificDetails.setUsername(username);
                Authentication authResult = new UserSpecificDetailsAuthenticationToken(defaultUserSpecificDetails);
                securityContext.setAuthentication(authResult);
            }
        }
        filterChain.doFilter(request, response);
    }
}
