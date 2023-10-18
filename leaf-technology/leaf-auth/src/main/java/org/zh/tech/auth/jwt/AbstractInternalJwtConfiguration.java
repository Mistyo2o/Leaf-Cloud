package org.zh.tech.auth.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.zh.thch.common.aes.AesEncryptor;
import org.zh.thch.common.basic.AppConstants;
import org.zh.thch.common.context.ApplicationContextBean;
import org.zh.thch.common.util.StringUtil;

/**
 * @author: zh
 * @description: 抽象的内部JWT配置。
 */
public abstract class AbstractInternalJwtConfiguration implements InternalJwtConfiguration{
    @Override
    public String getAppName() {
        return null;
    }

    @Override
    public int getExpiredIntervalSeconds() {
        return getExpiredIntervalSeconds(ApplicationContextBean.getActiveProfile());
    }

    /**
     * 获取JWT过期时间秒数
     *
     * @param profile 运行环境
     * @return JWT的过期时间秒数
     */
    protected int getExpiredIntervalSeconds(String profile) {
//        switch (profile) {
//            case Profiles.LOCAL:
//            case Profiles.DEV:
//                return 3600;
//            case Profiles.TEST:
//                return 1800;
//            default:
//                return 60;
//        }
        return 3600;
    }

}
