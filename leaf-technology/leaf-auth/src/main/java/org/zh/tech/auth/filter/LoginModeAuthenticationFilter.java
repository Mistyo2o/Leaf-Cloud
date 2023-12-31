package org.zh.tech.auth.filter;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.zh.tech.auth.authentication.AuthenticationTokenResolver;
import org.zh.tech.auth.exception.BusinessAuthenticationException;
import org.zh.thch.common.basic.LoginModeEnum;
import org.zh.thch.common.basic.exception.BusinessException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/9 16:27:56
 */
public class LoginModeAuthenticationFilter extends LoginAuthenticationFilter{

    public static final String PARAMETER_LOGIN_MODE = "loginMode";

    private AuthenticationTokenResolver<AbstractAuthenticationToken> defaultTokenResolver;
    private Map<String, AuthenticationTokenResolver<AbstractAuthenticationToken>> tokenResolverMapping = new HashMap<>();


    public LoginModeAuthenticationFilter(ApplicationContext context) {
        super(context);
        this.tokenResolverMapping.clear();
        context.getBeansOfType(AuthenticationTokenResolver.class).forEach((id, resolver) -> {
            String loginMode = resolver.getLoginMode();
            if (loginMode == null) {
                this.defaultTokenResolver = resolver;
            } else {
                this.tokenResolverMapping.put(loginMode, resolver);
            }
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //获取登录方式
        String loginMode = obtainLoginMode(request);
        AuthenticationTokenResolver<AbstractAuthenticationToken> resolver;
        if (loginMode == null){
            resolver = this.defaultTokenResolver;
        }else {
            resolver = this.tokenResolverMapping.get(loginMode);
        }

        if (loginMode != null){
            try {
                //从解决器中获取对应的认证实现
                AbstractAuthenticationToken authRequest  = resolver.resolveAuthenticationToken(request);
                setDetails(request, authRequest);
                return getAuthenticationManager().authenticate(authRequest);
            }catch (BusinessException e){
                throw new BusinessAuthenticationException(e);
            }
        }

        // 找不到匹配登录方式的构建器，则采用父类的用户名密码登录方式
        return super.attemptAuthentication(request, response);
    }


    public String obtainLoginMode(HttpServletRequest request) {
        String loginMode = request.getParameter(PARAMETER_LOGIN_MODE);
        if (StringUtils.isEmpty(loginMode)) { // 默认账户密码登录方式
            loginMode = LoginModeEnum.ACCOUNT.getValue();
        }
        return loginMode;
    }


    //参考UsernamePasswordAuthenticationFilter写法
    protected void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}
