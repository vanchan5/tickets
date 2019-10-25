package com.track.data.dto.manage.user.search;

import com.track.data.dto.base.BaseSearchDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-10-23 15:36
 *
 * 多条件分页获取用户列表
 *
 */
@Data
@ApiModel(description = "多条件分页获取用户列表")
@Accessors(chain = true)
public class SearchUsersDto extends BaseSearchDto {

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "是否启用 1启用 0禁用")
    private Boolean enabled;

    @ApiModelProperty(value = "用户类型:1-超级管理员，2-系统用户")
    private Integer userType;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "角色")
    private String roleName;
}
