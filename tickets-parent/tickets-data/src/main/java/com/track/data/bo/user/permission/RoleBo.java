package com.track.data.bo.user.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author cheng
 * @create 2019-10-19 17:32
 *
 * 角色业务处理对象
 */
@Data
@Accessors(chain = true)
public class RoleBo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一标识")
    private String id;

    @ApiModelProperty(value = "角色名 以ROLE_开头")
    private String name;

    @ApiModelProperty(value = "是否为注册默认角色")
    private Boolean defaultRole;

    @ApiModelProperty(value = "备注")
    private String description;

    @ApiModelProperty(value = "数据权限类型 0全部默认 1自定义")
    private Integer dataType;

    @ApiModelProperty(value = "角色级别")
    private Integer level;

    @ApiModelProperty(value = "系统类型 1-平台 2-商家")
    private Integer systemType;

    @ApiModelProperty(value = "店铺ID")
    private Long storeId;

}

