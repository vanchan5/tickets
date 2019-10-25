package com.track.data.dto.manage.permission.search;

import com.track.data.dto.base.BaseSearchDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-10-23 13:52
 *
 * 分页查询角色Dto
 */
@Data
@ApiModel(description = "分页查询角色Dto")
public class SearchRoleDto extends BaseSearchDto {

    @ApiModelProperty(value = "模糊查询名称")
    private String name;

    @ApiModelProperty(value = "角色级别 1-超级管理员 2-普通用户 3-财务 4-运营 5-客服 等等")
    private Integer level;

}
