package org.zh.business.admin.infrastructure.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.zh.business.admin.common.constant.AuthConstants;
import org.zh.tech.auth.authentication.*;
import org.zh.tech.auth.user.DefaultUserSpecificDetails;
import org.zh.tech.auth.user.UserSpecificDetailsService;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/9 14:22:54
 */

@Component
public class LoginAuthenticationProvider extends AbstractAuthenticationProvider<AbstractAuthenticationToken> {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserSpecificDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        DefaultUserSpecificDetails userSpecificDetails = (DefaultUserSpecificDetails) userDetailsService.loadUserByUsername(username);

        if (authentication instanceof DefaultAuthenticationToken){
            if (!userSpecificDetails.isEnabled()) { // 账户禁用状态拦截
                throw new DisabledException(AuthConstants.USER_DISABLED);
            }
            if (!userSpecificDetails.isAccountNonLocked()) {
                throw new LockedException(AuthConstants.USER_LOCKED);
            }
            if (!passwordEncoder.matches(password, userSpecificDetails.getPassword())) {
                throw new BadCredentialsException(AuthConstants.PWD_WRONG);
            }
        }

        if (authentication instanceof SmsVerifyCodeAuthenticationToken) {
            //TODO 短信令牌登录方式
        }

        WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
        String remoteAddress = webAuthenticationDetails.getRemoteAddress();
        UserSpecificDetailsAuthenticationToken authenticationToken = new UserSpecificDetailsAuthenticationToken(userSpecificDetails);
        authenticationToken.setIp(remoteAddress);
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UnauthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
