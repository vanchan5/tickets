package com.track.data.dto.manage.permission.save;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.track.common.constant.SecurityConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-10-28 23:30
 *
 * 保存菜单权限
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "保存菜单权限")
public class SavePermissionDto {

    @ApiModelProperty(value = "唯一标识")
    private Long id;

    @ApiModelProperty(value = "说明备注")
    private String description;

    @ApiModelProperty(value = "菜单/权限名称,小写英文-隔开,页面操作才需要name")
//    @NotNull(message = "菜单/权限名称不能为空")
    private String name;

    @ApiModelProperty(value = "父id")
    private Long parentId;

    @ApiModelProperty(value = "类型 -1顶部菜单 0页面 1具体操作")
    @NotNull(message = "类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "排序值")
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "前端组件路径，a-b/c-d/eFG")
    private String component;

    @ApiModelProperty(value = "页面路径/资源链接url,a-b")
    @NotNull(message = "页面路径path不能为空")
    private String path;

    @ApiModelProperty(value = "菜单标题，中文")
    @NotNull(message = "菜单标题不能为空")
    private String title;

    @ApiModelProperty(value = "系统类型 1-平台 2-商家 3-平台和商家",hidden = true)
    private Integer systemType = SecurityConstant.SYS_TYPE_MANAGER;

    @ApiModelProperty(value = "图标,默认为ios-navigate")
    private String icon = "ios-navigate";

    @ApiModelProperty(value = "层级,支持1-3级")
    @NotNull(message = "层级不能为空")
    private Integer level;

    @ApiModelProperty(value = "按钮权限类型",hidden = true)
    private String buttonType;

    @ApiModelProperty(value = "是否启用 默认1启用 0禁用")
    private Boolean enabled;

    @ApiModelProperty(value = "网页链接",hidden = true)
    private String url;

}
