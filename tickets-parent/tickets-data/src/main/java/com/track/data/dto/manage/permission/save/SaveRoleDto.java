package com.track.data.dto.manage.permission.save;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.track.common.constant.SecurityConstant;
import com.track.common.enums.manage.permission.RoleLevelEnum;
import com.track.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-10-22 21:45
 *
 * 保存角色信息--增改
 */
@Data
@ApiModel(description = "保存角色信息--增改")
@Accessors(chain = true)
public class SaveRoleDto {

    @ApiModelProperty(value = "角色唯一标识")
    @Min(0)
    private Long id;

    @ApiModelProperty(value = "角色名 建议以ROLE_开头")
    @NotNull(message = "角色名不能为空")
    private String name;

    @ApiModelProperty(value = "是否为注册默认角色")
    @NotNull(message = "是否为注册默认角色不能为空")
    private Boolean defaultRole;

    @ApiModelProperty(value = "备注")
    private String description;

    @ApiModelProperty(value = "数据权限类型 0全部默认 1自定义",hidden = true)
    private Integer dataType;

    @ApiModelProperty(value = "角色级别 1-超级管理员 2-普通用户 3-财务 4-运营 5-客服")
    @EnumConstraint(target = RoleLevelEnum.class)
    private Integer level;

    @ApiModelProperty(value = "系统类型 1-平台 2-商家,目前角色暂时只用到一个平台系统",hidden = true)
    private Integer systemType = SecurityConstant.SYS_TYPE_MANAGER;

    @ApiModelProperty(value = "店铺ID",hidden = true)
    @JSONField(serialize = false)
    private Long storeId;
}
