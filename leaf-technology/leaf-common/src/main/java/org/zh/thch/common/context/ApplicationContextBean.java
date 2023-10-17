package org.zh.thch.common.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.zh.thch.common.util.SpringUtil;


@Component
public class ApplicationContextBean implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(String beanName) {
        return SpringUtil.getBeanByName(applicationContext, beanName);
    }

    public static <T> T getBean(Class<T> type) {
        return SpringUtil.getBeanByDefaultName(applicationContext, type);
    }

    public static String getActiveProfile() {
        return SpringUtil.getActiveProfile(applicationContext);
    }

    public static boolean isActiveProfile(String profile) {
        return SpringUtil.isActiveProfile(applicationContext, profile);
    }
}
