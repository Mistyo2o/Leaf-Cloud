package org.zh.business.admin.modules.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.zh.business.admin.modules.system.service.SysUserService;
import org.zh.business.admin.modules.system.service.dto.SysUserDto;
import org.zh.business.admin.modules.system.service.entity.SysRole;
import org.zh.business.admin.modules.system.service.entity.SysUser;
import org.zh.business.admin.modules.system.service.repository.SysUserMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/12 18:57:37
 */

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUserDto getUserAndRole(String userName) {
        SysUser userParam = new SysUser();
        userParam.setUsername(userName);
        SysUserDto sysUserDto = this.baseMapper.findUserAndRoles(userParam);
        if (sysUserDto == null){
            return null;
        }

        List<SysRole> roles = sysUserDto.getRoles();
        if (CollectionUtil.isNotEmpty(roles)){
            Set<String> roleCodes = roles.stream().map(SysRole::getRoleCode).collect(Collectors.toSet());
            sysUserDto.setRoleCodes(roleCodes);
        }else {
            sysUserDto.setRoleCodes(null);
        }
        return sysUserDto;
    }
}
