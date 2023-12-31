package org.zh.business.admin.modules.system.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/12/13 13:49:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_permission")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    @TableField("permission_name")
    private String permissionName;

    @TableField("permission_code")
    private String permissionCode;

    @TableField("description")
    private String description;

    @TableField("resources_id")
    private String resourcesId;

    @TableField("resources_type")
    private String resourcesType;

    @TableField("source")
    private String source;

    @TableField("operation_scope")
    private String operationScope;


}
