package com.track.data.dto.manage.feedBack.search;

import com.track.data.dto.base.BaseSearchDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-10-30 22:41
 *
 * 分页查询反馈信息条件
 *
 */
@Data
@ApiModel(description = "分页查询反馈信息条件")
@Accessors(chain = true)
public class SearchFeedBackDto extends BaseSearchDto {

    @ApiModelProperty(value = "状态:0-未处理，1-处理")
    private Integer state;

    @ApiModelProperty(value = "关键字")
    private String keyWord;

    @ApiModelProperty("反馈开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "反馈截止时间")
    private LocalDateTime endTime;

}