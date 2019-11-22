package com.track.data.dto.applet.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @Description 完善用户信息昵称，性别
 * @since 2019/11/22 17:30
 */
@Data
@ApiModel(description = "完善用户信息昵称，性别")
public class ImproveUserInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微信获取用户信息  性别 0：未知、1：男、2：女 ")
    private Integer gender;

    @ApiModelProperty(value = "微信获取用户信息  昵称")
    private String nickName;

}

