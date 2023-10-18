package org.zh.business.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.zh.tech.auth.SecurityModule;

/**
 * @author zhouHui
 * @description 后台管理服务入口
 */
@SpringBootApplication
@Import(SecurityModule.class)
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
