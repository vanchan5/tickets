package com.track.data.dto.manage.permission.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-10-28 21:44
 *
 * 修改默认角色
 */
@ApiModel(description = "修改默认角色")
@Accessors(chain = true)
@Data
public class EditDefaultRoleDto {

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "是否设置默认角色 true-是，false-取消")
    private Boolean isDefault;
}
