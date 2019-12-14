package com.track.data.vo.applet.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @Description 小程序用户信息
 * @since 2019/12/14 15:46
 */
@Data
@ApiModel(description = "小程序用户信息")
public class UserMessageVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "手机号码")
    private String phone;

}
