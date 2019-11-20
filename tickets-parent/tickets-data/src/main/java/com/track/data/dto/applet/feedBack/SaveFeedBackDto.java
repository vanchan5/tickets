package com.track.data.dto.applet.feedBack;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-11-19 10:36
 *
 * 保存反馈信息
 */
@Data
@ApiModel(description = "保存反馈信息")
@Accessors(chain = true)
public class SaveFeedBackDto {

    @ApiModelProperty(value = "反馈内容")
    @NotNull(message = "反馈内容不能为空")
    private String content;
}
