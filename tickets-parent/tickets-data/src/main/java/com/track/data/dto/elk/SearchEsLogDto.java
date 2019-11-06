package com.track.data.dto.elk;

import com.track.data.dto.base.BaseSearchDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-11-06 19:41
 *
 * 分页查询elasticsearch日志条件
 */
@Data
@ApiModel(description = "分页查询elasticsearch日志条件")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SearchEsLogDto {

    @ApiModelProperty(value = "起始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "日志类型 0登陆日志 1操作日志")
    private Integer logType;

    @ApiModelProperty(value = "关键字")
    private String key;

    @ApiModelProperty(value = "分页条件")
    private BaseSearchDto pageInfo;
}
