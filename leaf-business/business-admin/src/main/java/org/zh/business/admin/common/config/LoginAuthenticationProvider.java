package org.zh.business.admin.common.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.zh.tech.auth.authentication.provider.AbstractAuthenticationProvider;

/**
 * @author zhouHui
 * @description 登录服务端认证提供者
 */

@Component
public class LoginAuthenticationProvider extends AbstractAuthenticationProvider<AbstractAuthenticationToken> {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
//        DefaultUserSpecificDetails userSpecificDetails = (DefaultUserSpecificDetails) userDetailsService.loadUserByUsername(username);
//        if (authentication instanceof DefaultAuthenticationToken) { // 用户名密码令牌方式
//            if (!userSpecificDetails.isEnabled()) { // 账户禁用状态拦截
//                throw new DisabledException(SystemConstants.USER_DISABLED);
//            }
//            if (!userSpecificDetails.isAccountNonLocked()) {
//                throw new LockedException(SystemConstants.USER_LOCKED);
//            }
//            if (!passwordEncoder.matches(password, userSpecificDetails.getPassword())) {
//                throw new BadCredentialsException(SystemConstants.PWD_WRONG);
//            }
//        }
//        if (authentication instanceof SmsVerifyCodeAuthenticationToken) { // 短信令牌登录方式
//        }
//        WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
//        String remoteAddress = webAuthenticationDetails.getRemoteAddress();
//        UserSpecificDetailsAuthenticationToken authenticationToken = new UserSpecificDetailsAuthenticationToken(userSpecificDetails);
//        authenticationToken.setIp(remoteAddress);
//        // 在线用户登录限制
//        this.onlineUserLimit(username);
        return authentication;
    }
}
