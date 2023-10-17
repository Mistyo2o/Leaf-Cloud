package org.zh.thch.common.basic;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginModeEnum {

    //用户名密码登录
    ACCOUNT("account"),
    //短信登录
    SMS("sms");

    private String value;
}
