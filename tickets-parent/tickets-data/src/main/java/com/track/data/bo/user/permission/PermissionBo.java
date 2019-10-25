package com.track.data.bo.user.permission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-10-19 17:34
 *
 * 用户绑定的权限业务对象
 */
@Data
@Accessors(chain = true)
public class PermissionBo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一标识")
    private Long id;

    @ApiModelProperty(value = "说明备注")
    private String description;

    @ApiModelProperty(value = "菜单/权限名称")
    private String name;

    @ApiModelProperty(value = "父id")
    private String parentId;

    @ApiModelProperty(value = "类型 0页面 1具体操作")
    private Integer type;

    @ApiModelProperty(value = "排序值")
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "前端组件")
    private String component;

    @ApiModelProperty(value = "页面路径/资源链接url")
    private String path;

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "系统类型 1-平台 2-商家 3-平台和商家")
    private Integer systemType;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "按钮权限类型")
    private String buttonType;

    @ApiModelProperty(value = "是否启用 1启用 0禁用")
    private Boolean enabled;

    @ApiModelProperty(value = "网页链接")
    private String url;
}
