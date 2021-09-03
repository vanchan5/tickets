package com.track.login.details.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author cheng
 * @create 2021/9/3 10:13
 *
 * 用户业务处理对象
 */
@Data
@Accessors(chain = true)
public class UmUserBo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "0：未知、1：男、2：女")
    private Integer sex;

    @ApiModelProperty(value = "是否启用 1启用 0禁用")
    private Boolean enabled;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像")
    private String photo;

    @ApiModelProperty(value = "微信openId")
    private String openId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "用户类型:1-超级管理员，2-系统普通用户,3-消费者")
    private Integer userType;

}

