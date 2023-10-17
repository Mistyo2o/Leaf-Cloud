package org.zh.thch.common.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.zh.thch.common.beans.ContextInitializedBean;
import org.zh.thch.common.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 容器初始化完成后执行bean的监听器，找出所有容器初始化完成后执行bean并在容器初始化完成后执行。
 * 如果一个bean具有代理，则只执行代理
 */

@Component
public class ContextInitializedBeanListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Map<String, ContextInitializedBean> beans = applicationContext.getBeansOfType(ContextInitializedBean.class);
        Map<ContextInitializedBean, ContextInitializedBean> map = new HashMap<>();
        for (ContextInitializedBean bean  : beans.values()) {
            map.putIfAbsent(bean, bean);
        }

        for (ContextInitializedBean bean : map.values()) {
            try {
                bean.afterInitialized(applicationContext);
            } catch (Exception e) {
                LogUtil.error(getClass(), e);
            }
        }
    }
}
