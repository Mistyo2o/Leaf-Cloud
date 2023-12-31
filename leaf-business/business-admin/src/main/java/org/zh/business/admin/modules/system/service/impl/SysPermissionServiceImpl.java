package org.zh.business.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zh.business.admin.modules.system.service.SysPermissionService;
import org.zh.business.admin.modules.system.service.entity.SysPermission;
import org.zh.business.admin.modules.system.service.entity.SysRolePermission;
import org.zh.business.admin.modules.system.service.repository.SysPermissionMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/13 13:54:08
 */

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Autowired
    private SysRolePermissionServiceImpl rolePermissionService;

    @Override
    public List<SysPermission> getPermissions(Integer roleId) {
        List<SysPermission> sysPermissions = new ArrayList<>();
        if (roleId == null) {
            return sysPermissions;
        }
        LambdaQueryWrapper<SysRolePermission> rolePermissionWrapper = new LambdaQueryWrapper<>();
        rolePermissionWrapper.eq(SysRolePermission::getRoleId, roleId);
        List<SysRolePermission> rolePermissions = rolePermissionService.list(rolePermissionWrapper);
        if (!rolePermissions.isEmpty()) {
            List<Integer> permissionIds = rolePermissions.stream()
                    .map(SysRolePermission::getPermissionId)
                    .collect(Collectors.toList());
            sysPermissions = this.listByIds(permissionIds);
        }
        return sysPermissions;
    }
}
