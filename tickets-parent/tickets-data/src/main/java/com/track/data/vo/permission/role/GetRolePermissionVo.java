package com.track.data.vo.permission.role;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-10-28 21:59
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "菜单权限Vo")
public class GetRolePermissionVo {

    @ApiModelProperty("菜单权限Id")
    private Long id;

    @ApiModelProperty("菜单权限名称")
    private String title;

    @ApiModelProperty("该角色是否包含该权限")
    private Boolean checked = false;

    @ApiModelProperty("父级ID")
    private String parentId;

    @ApiModelProperty("子级")
    @JSONField(ordinal = 4)
    private List<GetRolePermissionVo> children;
}
