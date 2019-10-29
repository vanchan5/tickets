package com.track.data.dto.manage.permission.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-10-29 12:25
 *
 * 添加/编辑角色分配菜单权限
 */
@Data
@ApiModel(description = "添加/编辑角色分配菜单权限")
@Accessors(chain = true)
public class SaveRolePermissionsDto {

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = " 权限IDs")
    @NotEmpty(message = "权限IDs不能为空")
    private List<Long> permissionIds;
}
