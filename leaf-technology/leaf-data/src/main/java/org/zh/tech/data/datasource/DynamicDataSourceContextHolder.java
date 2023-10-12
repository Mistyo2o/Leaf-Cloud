package org.zh.tech.data.datasource;

/**
 * @author: zh
 * @description: 数据源上下文处理器
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> dataSourceHolder = new ThreadLocal<>();

    public static void setDataSourceKey(String key){
        dataSourceHolder.set(key);
    }

    public static String getDataSourceKey(){
        return dataSourceHolder.get();
    }

    public static void clearDataSourceKey(){
        dataSourceHolder.remove();
    }
}
