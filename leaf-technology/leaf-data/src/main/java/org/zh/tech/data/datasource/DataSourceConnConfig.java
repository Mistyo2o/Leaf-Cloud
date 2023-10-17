package org.zh.tech.data.datasource;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhouHui
 * @description 数据库连接信息
 */
@Getter
@Setter
public class DataSourceConnConfig {
    /**
     * 数据库连接url
     */
    private String url;
    /**
     * 连接驱动名称
     */
    private String driverName;
    /**
     * 数据库用户名
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;
}
