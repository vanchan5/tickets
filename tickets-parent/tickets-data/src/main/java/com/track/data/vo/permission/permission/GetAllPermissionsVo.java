package com.track.data.vo.permission.permission;

import com.alibaba.fastjson.annotation.JSONField;
import com.track.common.constant.SecurityConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-10-29 10:46
 *
 * 获取所有的菜单权限
 */
@Data
@ApiModel(description = "获取所有的菜单权限")
@Accessors(chain = true)
public class GetAllPermissionsVo {

    @ApiModelProperty(value = "唯一标识")
    @JSONField(ordinal = 0)
    private Long id;

    @ApiModelProperty(value = "父id")
    @JSONField(ordinal = 1)
    private Long parentId;

    @ApiModelProperty(value = "前端组件路径，a-b/c-d/eFG")
    @JSONField(ordinal = 2)
    private String component;

    @ApiModelProperty(value = "页面路径/资源链接url,a-b")
    @JSONField(ordinal = 3)
    private String path;

    @ApiModelProperty(value = "菜单标题，中文")
    @JSONField(ordinal =4 )
    private String title;

    @ApiModelProperty(value = "图标,默认为ios-navigate")
    @JSONField(ordinal = 5)
    private String icon = "ios-navigate";

    @ApiModelProperty(value = "层级,支持1-3级")
    @JSONField(ordinal =6 )
    private Integer level;

    @ApiModelProperty(value = "类型 -1顶部菜单 0页面 1具体操作")
    @JSONField(ordinal = 7)
    private Integer type;

    @ApiModelProperty(value = "菜单/权限名称,小写英文-隔开,页面操作才需要name")
    @JSONField(ordinal = 8)
    private String name;

    @ApiModelProperty(value = "说明备注")
    @JSONField(ordinal = 9)
    private String description;

    @ApiModelProperty(value = "排序值")
    @JSONField(ordinal = 10)
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "系统类型 1-平台 2-商家 3-平台和商家",hidden = true)
    @JSONField(ordinal = 11)
    private Integer systemType = SecurityConstant.SYS_TYPE_MANAGER;

    @ApiModelProperty(value = "按钮权限类型")
    @JSONField(ordinal = 12)
    private String buttonType;

    @ApiModelProperty(value = "是否启用 默认1启用 0禁用")
    @JSONField(ordinal = 13)
    private Boolean enabled;

    @ApiModelProperty(value = "网页链接")
    @JSONField(ordinal = 14)
    private String url;

    @ApiModelProperty(value = "子集")
    @JSONField(ordinal = 15)
    private List<GetAllPermissionsVo> children;

}

