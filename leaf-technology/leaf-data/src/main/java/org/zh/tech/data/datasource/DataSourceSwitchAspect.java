package org.zh.tech.data.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.zh.tech.data.annotation.DataSourceSwitch;

import java.lang.reflect.Method;

/**
 * @author zhouHui
 * @description 数据源切换切面类
 */
@Aspect
@Component
public class DataSourceSwitchAspect {

    @Pointcut("@annotation(org.zh.tech.data.annotation.DataSourceSwitch)")
    public void dataSourcePointcut(){
    }

    @Around("dataSourcePointcut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        DataSourceSwitch dataSourceSwitch = method.getAnnotation(DataSourceSwitch.class);
        if (dataSourceSwitch != null){
            //设置数据源
            DynamicDataSourceContextHolder.setDataSourceKey(dataSourceSwitch.value());
        }else {
            //设置默认数据源
            DynamicDataSourceContextHolder.setDataSourceKey("");
        }
        try {
            return point.proceed();
        } finally {
            DynamicDataSourceContextHolder.clearDataSourceKey();
        }
    }
}
