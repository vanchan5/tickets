package com.track.data.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-10-23 20:56
 *
 * 启用禁用
 */
@Data
@ApiModel(description = "启用或禁用")
public class EditEnabledDto {

    @NotNull(message = "id参数不能为空")
    private Long[] ids;

    @ApiModelProperty("改变状态 1：启用/通过  0：禁用/驳回")
    @NotNull(message = "enabled参数不能为空")
    private Boolean enabled;

}
