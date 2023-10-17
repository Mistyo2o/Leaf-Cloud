package org.zh.tech.data.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataSourceSwitch {
    //TODO 暂时不设置默认值
    String value();
}
