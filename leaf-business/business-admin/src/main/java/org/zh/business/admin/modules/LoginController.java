package org.zh.business.admin.modules;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zh.thch.common.restful.RestfulResult;

/**
 * @author zhouHui
 * @description 后台管理系统登录入口
 */
@RestController
@RequestMapping("/admin")
public class LoginController {

    @PostMapping("/login")
    public RestfulResult login(@RequestParam String username, @RequestParam String password,
                               @RequestParam(required = false) String loginMode) {
        // 用于安全认证登录引导，无需处理任何逻辑
        System.out.println(111);
        return RestfulResult.success(username + password + loginMode);
    }

}
