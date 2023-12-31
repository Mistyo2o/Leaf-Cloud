package org.zh.business.admin.modules.system.service.dto;

import lombok.Getter;
import lombok.Setter;
import org.zh.business.admin.modules.system.service.entity.SysRole;
import org.zh.business.admin.modules.system.service.entity.SysUser;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/12 18:53:15
 */

@Setter
@Getter
public class SysUserDto implements Serializable {

    private Integer userId;
    private SysUser user;
    private List<SysRole> roles;
    private Set<String> roleCodes = new HashSet<>();

}
