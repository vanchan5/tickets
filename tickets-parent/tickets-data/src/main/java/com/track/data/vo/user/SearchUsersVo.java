package com.track.data.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.track.data.vo.base.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-10-23 15:32
 *
 * 多条件分页获取用户列表
 *
 */
@Data
@ApiModel(description = "多条件分页获取用户列表")
@Accessors(chain = true)
public class SearchUsersVo {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "用户头像")
    private String photo;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "是否启用 1启用 0禁用")
    private Boolean enabled;;

    @ApiModelProperty(value = "用户类型:1-超级管理员，2-系统用户,3-消费者")
    private Integer userType;

    @ApiModelProperty(value = "用户名/账号")
    private String username;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "关联的角色")
    private List<BaseVo> roleList;

    @ApiModelProperty(value = "关联角色的名称")
    private String roleName;
}
