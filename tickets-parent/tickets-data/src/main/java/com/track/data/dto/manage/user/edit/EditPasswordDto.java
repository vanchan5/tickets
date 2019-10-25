package com.track.data.dto.manage.user.edit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-10-25 17:26
 *
 * 修改用户密码
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "修改用户密码")
public class EditPasswordDto {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "密码")
    private String password;
}
