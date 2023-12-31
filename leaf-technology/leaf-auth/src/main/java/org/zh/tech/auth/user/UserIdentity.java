package org.zh.tech.auth.user;

import java.io.Serializable;

public interface UserIdentity<K extends Serializable> extends Serializable {
    default String getType() {
        return null;
    }

    K getId();

}
