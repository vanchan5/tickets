package com.track.data.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-11-12 19:56
 *
 * 多条件分页获取Applet用户列表
 */
@Data
@ApiModel(description = "多条件分页获取Applet用户列表")
@Accessors(chain = true)
public class SearchAppletUsersVo {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "微信号")
    private String wechat;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "是否启用 1启用 0禁用")
    private Boolean enabled;
}
