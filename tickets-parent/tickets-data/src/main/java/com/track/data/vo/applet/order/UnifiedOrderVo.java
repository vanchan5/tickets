package com.track.data.vo.applet.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/9/4 21:35
 */
@Data
@ApiModel(value = "UnifiedOrderVo", description = "调起支付参数")
public class UnifiedOrderVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @JSONField(serialize = false)
    @ApiModelProperty(value = "应用ID")
    private String appId;

    @ApiModelProperty(value = "时间戳从1970年1月1日00:00:00至今的秒数,即当前的时间")
    private String timestamp;

    @ApiModelProperty(value = "随机字符串，长度为32个字符以下")
    private String nonceStr;

    @ApiModelProperty(value = "统一下单接口返回的 prepay_id 参数值，提交格式如：prepay_id=*")
    private String packageStr;

    @ApiModelProperty(value = "签名类型，默认为MD5")
    private String signType;

    @ApiModelProperty(value = "签名")
    private String paySign;

}
