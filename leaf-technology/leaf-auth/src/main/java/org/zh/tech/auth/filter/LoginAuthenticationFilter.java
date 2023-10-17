package org.zh.tech.auth.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhouHui
 * @description 登录认证过滤器
 */
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

}
