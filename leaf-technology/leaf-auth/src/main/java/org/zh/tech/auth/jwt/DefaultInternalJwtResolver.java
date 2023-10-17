package org.zh.tech.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.ApplicationContext;
import org.zh.thch.common.basic.Strings;
import org.zh.thch.common.beans.ContextInitializedBean;
import org.zh.thch.common.util.JacksonUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/10/17 20:50:34
 */
public class DefaultInternalJwtResolver implements JwtResolver, ContextInitializedBean {

    private static final String JWT_PREFIX = "jwt:";

    private Map<String, InternalJwtConfiguration> configurationMap = new HashMap<>();
    private Map<String, JWTVerifier> verifierMap = new HashMap<>();
    @Override
    public boolean isGenerable(String appName) {
        return false;
    }

    @Override
    public String generate(String appName, Object source) {
        if (source != null) {
            InternalJwtConfiguration configuration = getConfiguration(appName);
            long expiredTimeMillis = System.currentTimeMillis() + configuration.getExpiredIntervalSeconds() * 1000L;
            try {
                String audienceJson = JacksonUtil.CLASSED_MAPPER.writeValueAsString(source);
                String token = JWT.create()
                        .withExpiresAt(new Date(expiredTimeMillis))
                        .withAudience(audienceJson)
                        .sign(Algorithm.HMAC256(configuration.getSecretKey()));
                // 形如：jwt:[appName]/token
                return JWT_PREFIX + appName + Strings.SLASH + token;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public boolean isParsable() {
        return false;
    }

    @Override
    public <T> T parse(String jwt, Class<T> type) {
        return null;
    }

    @Override
    public boolean verify(String jwt) {
        return false;
    }



    /**
     * @description 从Spring容器上下文加载Jwt配置信息
     * @param context Spring容器上下文
     */
    @Override
    public void afterInitialized(ApplicationContext context) throws Exception {
        context.getBeansOfType(InternalJwtConfiguration.class).values().forEach(configuration -> {
            String appName = configuration.getAppName();
            if (appName == null) {
                appName = Strings.EMPTY;
            }
            this.configurationMap.put(appName, configuration);
        });
    }

    private InternalJwtConfiguration getConfiguration(String appName) {
        InternalJwtConfiguration configuration = this.configurationMap.get(appName);
        if (configuration == null) {
            configuration = this.configurationMap.get(Strings.EMPTY);
        }
        return configuration;
    }
}
