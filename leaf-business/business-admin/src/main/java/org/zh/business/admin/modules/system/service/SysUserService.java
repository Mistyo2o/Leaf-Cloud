package org.zh.business.admin.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zh.business.admin.modules.system.service.dto.SysUserDto;
import org.zh.business.admin.modules.system.service.entity.SysUser;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/12 18:52:02
 */
public interface SysUserService extends IService<SysUser> {

    SysUserDto getUserAndRole(String userName);
}
