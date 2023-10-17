package org.zh.tech.auth.jwt;


/**
 * 内部JWT解决器
 */
public interface JwtResolver {

    boolean isGenerable(String appName);

    String generate(String appName, Object source);

    boolean isParsable();

    <T> T parse(String jwt, Class<T> type);

    boolean verify(String jwt);
}
