package com.track.data.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-10-25 21:51
 *
 * 验证码实体
 */
@Data
@ApiModel(description = "验证码实体")
@Accessors(chain = true)
public class CaptchaVo {

    @ApiModelProperty(value = "验证码id")
    private String captchaId;

    @ApiModelProperty(value = "验证码")
    private String code;
}
