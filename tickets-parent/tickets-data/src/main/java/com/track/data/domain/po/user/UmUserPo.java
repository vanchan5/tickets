package com.track.data.domain.po.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author admin
 * @since 2019-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("um_user")
@ApiModel(value = "UmUserPo对象", description = "用户")
public class UmUserPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "前端用户id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "创建者Id")
    private Long createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者Id")
    private Long updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    @TableLogic
    private Boolean delFlag;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "0：未知、1：男、2：女")
    private Integer sex;

    @ApiModelProperty(value = "状态 默认1启用 0禁用")
    private Boolean enabled;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像")
    private String photo;

    @ApiModelProperty(value = "微信openId")
    private String openId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "用户类型:1-超级管理员，2-系统普通用户,3-消费者")
    private Integer userType;

}
