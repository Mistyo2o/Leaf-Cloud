package org.zh.tech.auth.user;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/9 18:24:59
 */
public class DefaultUserSpecificDetails extends SimpleUserSpecificDetails<DefaultUserIdentity> {

    private static final long serialVersionUID = 9017622032464226982L;

    private String password;

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
