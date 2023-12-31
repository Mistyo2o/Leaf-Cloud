package org.zh.business.admin.modules.system.service.repository;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.zh.business.admin.modules.system.service.dto.SysUserDto;
import org.zh.business.admin.modules.system.service.entity.SysUser;

@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUserDto findUserAndRoles(SysUser user);
}
