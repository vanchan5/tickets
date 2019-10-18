package com.track.data.bo.applet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 小程序通过登录凭证code获取openId，sessionKey等信息
 * @author yeJH
 * @since 2019/10/16 22:39
 */
@Data
public class CodeToSessionBo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "openId 用户唯一标识")
    private String openId;

    @ApiModelProperty(value = "用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回")
    private String unionId;

    @ApiModelProperty(value = "会话密钥")
    private String sessionKey;

    @ApiModelProperty(value = "错误码")
    private Integer errCode;

    @ApiModelProperty(value = "错误信息")
    private String errMsg;

}
