package com.track.data.dto.manage.user.save;

import com.track.common.constant.SecurityConstant;
import com.track.common.constant.login.LoginConstant;
import com.track.common.enums.manage.user.UserTypeEnum;
import com.track.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-10-17 09:07
 *
 * 保存用户信息
 *
 */
@ApiModel(description = "保存用户信息")
@Data
@Accessors(chain = true)
public class SaveUserDto {

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String nickName = SecurityConstant.USER_DEFAULT_NICKNAME;

    @ApiModelProperty(value = "0-女  1-男 2-保密")
    private Integer sex = SecurityConstant.USER_DEFAULT_SEX;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像")
    private String photo = SecurityConstant.USER_DEFAULT_AVATAR;

    @ApiModelProperty(value = "微信openId")
    private String openId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "用户类型:1-超级管理员，2-系统普通用户,3-消费者")
    @NotNull(message = "用户类型不能为空")
    @EnumConstraint(target = UserTypeEnum.class)
    private Integer userType;

    @ApiModelProperty(value = "绑定的角色id")
    private List<Long> roleIds;

}
