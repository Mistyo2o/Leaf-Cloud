package org.zh.business.admin.security.authentication;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;
import org.zh.business.admin.common.constant.AuthConstants;
import org.zh.business.admin.modules.system.service.SysPermissionService;
import org.zh.business.admin.modules.system.service.SysUserService;
import org.zh.business.admin.modules.system.service.dto.SysUserDto;
import org.zh.business.admin.modules.system.service.entity.SysPermission;
import org.zh.business.admin.modules.system.service.entity.SysRole;
import org.zh.business.admin.modules.system.service.entity.SysUser;
import org.zh.tech.auth.user.DefaultUserIdentity;
import org.zh.tech.auth.user.DefaultUserSpecificDetails;
import org.zh.tech.auth.user.UserGrantedAuthority;
import org.zh.tech.auth.user.UserSpecificDetailsService;
import org.zh.tech.core.exception.param.RequiredParamException;
import org.zh.thch.common.basic.Strings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/12 18:33:23
 */

@Component
public class UserSpecificDetailsServiceImpl implements UserSpecificDetailsService {

    @Autowired
    private SysUserService userService;
    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)){
            throw new RequiredParamException();
        }
        //查询用户信息
        SysUserDto sysUserDto = userService.getUserAndRole(username);
        if (sysUserDto == null || sysUserDto.getUser() == null){
            throw new UsernameNotFoundException(AuthConstants.USERNAME_NOT_EXIST);
        }
        //拼装用户信息
        SysUser sysUser = sysUserDto.getUser();
        DefaultUserSpecificDetails userSpecificDetails = new DefaultUserSpecificDetails();
        userSpecificDetails.setUsername(sysUser.getUsername());
        userSpecificDetails.setCaption(sysUser.getNickname());
        // 从数据库中直接取加密密码
        String encodedPassword = sysUser.getPassword();
        userSpecificDetails.setPassword(encodedPassword);
        userSpecificDetails.setIdentity(new DefaultUserIdentity(sysUser.getUserType(), sysUser.getId()));
        // 添加角色鉴权以及权限鉴权，每次访问带有权限限制的接口时就会验证，拥有对应权限code的话才可以正常访问。
        List<GrantedAuthority> authorities = new ArrayList<>();
        UserGrantedAuthority grantedAuthority = new UserGrantedAuthority();
        grantedAuthority.setType(sysUser.getUserType());
        grantedAuthority.setRank(sysUser.getUserRank());
        grantedAuthority.setApp(Strings.ASTERISK);
        grantedAuthority.setPermissions(this.buildPermissions(sysUserDto.getRoles()));
        authorities.add(grantedAuthority);
        userSpecificDetails.setAuthorities(authorities);
        // 账户状态配置
        userSpecificDetails.setEnabled(sysUser.getEnabled());
        userSpecificDetails.setAccountNonLocked(true);
        userSpecificDetails.setAccountNonExpired(true);
        userSpecificDetails.setCredentialsNonExpired(true);
        return userSpecificDetails;
    }

    /**
     * 通过角色集构建权限限定集
     *
     * @param roles 角色集
     * @return 权限限定集
     */
    private Set<String> buildPermissions(List<SysRole> roles) {
        Set<String> permissions = new HashSet<>();
        if (CollectionUtils.isNotEmpty(roles)) {
            List<SysPermission> sysPermissions = new ArrayList<>();
            roles.forEach(role -> {
                List<SysPermission> permission = permissionService.getPermissions(role.getId());
                sysPermissions.addAll(permission);
            });
            permissions = sysPermissions.stream().map(SysPermission::getPermissionCode).collect(Collectors.toSet());
        }
        return permissions;
    }
}
