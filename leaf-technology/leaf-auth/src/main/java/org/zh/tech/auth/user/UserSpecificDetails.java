package org.zh.tech.auth.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.security.core.userdetails.UserDetails;


@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface UserSpecificDetails <I extends UserIdentity<?>> extends UserSpecific<I>, UserDetails {

    I getIdentity();

    String getCaption();

    UserSpecificDetails<I> clone();

}
