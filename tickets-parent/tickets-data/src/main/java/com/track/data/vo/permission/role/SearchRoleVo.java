package com.track.data.vo.permission.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-10-23 13:31
 *
 * 分页查询角色信息
 */
@Data
@ApiModel(description = "分页查询角色信息")
public class SearchRoleVo {

    @ApiModelProperty(value = "角色唯一标识")
    private Long id;

    @ApiModelProperty(value = "角色名 建议以ROLE_开头")
    private String name;

    @ApiModelProperty(value = "是否为注册默认角色,true-是，false-否")
    private Boolean defaultRole;

    @ApiModelProperty(value = "备注")
    private String description;

    @ApiModelProperty(value = "角色级别")
    private Integer level;
}
