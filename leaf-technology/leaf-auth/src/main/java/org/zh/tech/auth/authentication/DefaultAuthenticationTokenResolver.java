package org.zh.tech.auth.authentication;

import org.apache.commons.lang3.StringUtils;
import org.zh.tech.auth.user.UsernamePassword;
import org.zh.thch.common.basic.Strings;
import org.zh.thch.common.util.JsonUtil;
import org.zh.thch.common.util.WebHttpUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/9 16:42:50
 */
public class DefaultAuthenticationTokenResolver extends AuthenticationTokenResolverImpl<DefaultAuthenticationToken> {

    protected static final String DEFAULT_PARAMETER_USERNAME = "username";
    protected static final String DEFAULT_PARAMETER_PASSWORD = "password";

    public DefaultAuthenticationTokenResolver(String loginMode) {
        super(loginMode);
    }

    @Override
    public DefaultAuthenticationToken resolveAuthenticationToken(HttpServletRequest request) {
        //优先从请求Parameter中获取
        String username = WebHttpUtil.getParameterOrAttribute(request, DEFAULT_PARAMETER_USERNAME);
        String password = WebHttpUtil.getParameterOrAttribute(request, DEFAULT_PARAMETER_PASSWORD);

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            //从请求body中获取
            String bodyString = WebHttpUtil.getRequestBodyString(request);
            if (StringUtils.isNotBlank(bodyString)) {
                UsernamePassword usernamePassword = JsonUtil.json2Bean(bodyString, UsernamePassword.class);
                if (usernamePassword != null) {
                    username = usernamePassword.getUsername();
                    password = usernamePassword.getPassword();
                }
            }
        }
        // 规避trim空指针异常
        if (StringUtils.isEmpty(username)) {
            username = Strings.EMPTY;
        }
        if (StringUtils.isEmpty(password)) {
            password = Strings.EMPTY;
        }
        return new DefaultAuthenticationToken(username.trim(), password.trim());
    }
}
