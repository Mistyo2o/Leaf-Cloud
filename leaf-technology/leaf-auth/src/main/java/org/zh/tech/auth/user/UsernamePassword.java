package org.zh.tech.auth.user;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/9 16:47:22
 */


public class UsernamePassword {

    private String username;
    private String password;

    public UsernamePassword() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
