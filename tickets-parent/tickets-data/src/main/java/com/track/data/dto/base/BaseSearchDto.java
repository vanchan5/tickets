package com.track.data.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @Author cheng
 * @create 2019-09-02 09:12
 *
 * 分页查询基础Dto,分页查询请继承这个类
 */
@Data
@ApiModel(description = "分页查询基础Dto")
@Accessors(chain = true)
public class BaseSearchDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
