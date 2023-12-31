package org.zh.business.admin.modules;

import org.springframework.web.bind.annotation.*;
import org.zh.thch.common.restful.RestfulResult;

/**
 * @author zhouHui
 * @description 系统登录入口
 */
@RestController
public class LoginController {


    /**
     * @description 登录引导 具体逻辑LoginAuthenticationProvider 实现
     * @date zhouHui 15:16:34
     */
    @PostMapping("/login")
    public RestfulResult login(@RequestParam String username, @RequestParam String password,
                               @RequestParam(required = false) String loginMode) {
        return RestfulResult.success(username + password + loginMode);
    }


    @PostMapping("/logout")
    public RestfulResult logout() {
        // 用于登出流程引导，无需处理任何逻辑
        return RestfulResult.success();
    }

    @GetMapping("/hello")
    public RestfulResult<String> login() {
        return RestfulResult.success("hello！！！");
    }
}
