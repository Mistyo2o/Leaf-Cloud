package org.zh.business.admin.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zh.business.admin.modules.system.service.entity.SysPermission;

import java.util.List;

public interface SysPermissionService extends IService<SysPermission> {

    List<SysPermission> getPermissions(Integer roleId);
}
