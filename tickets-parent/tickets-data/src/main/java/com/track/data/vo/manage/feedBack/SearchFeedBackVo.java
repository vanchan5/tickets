package com.track.data.vo.manage.feedBack;

import com.track.data.dto.base.BaseSearchDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-10-30 22:35
 *
 * 条件分页查询反馈信息
 */
@Data
@ApiModel(description ="条件分页查询反馈信息" )
@Accessors(chain = true)
public class SearchFeedBackVo {

    @ApiModelProperty(value = "反馈ID")
    private Long id;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "反馈时间")
    private LocalDateTime feedBackTime;

    @ApiModelProperty(value = "状态:0-未处理，1-处理")
    private Integer state;

    @ApiModelProperty(value = "处理人")
    private String dealPeople;

    @ApiModelProperty(value = "处理时间")
    private LocalDateTime dealTime;
}
